package com.example.auth.security;

import com.example.auth.domain.enums.AuthError;
import com.example.common.domain.entity.Credential;
import com.example.common.domain.entity.User;
import com.example.common.repository.CredentialRepository;
import com.example.common.repository.UserRepository;
import com.example.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.stereotype.Component;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.util.Optional;

@Component
public class UserAuthFilter extends AbstractUserAuthFilter {

    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final CredentialRepository credentialRepository;

    public UserAuthFilter(TokenService tokenService,
                          UserRepository userRepository,
                          CredentialRepository credentialRepository,
                          ObjectMapper objectMapper) {
        super(objectMapper);
        this.tokenService = tokenService;
        this.userRepository = userRepository;
        this.credentialRepository = credentialRepository;
    }

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain)
            throws IOException, jakarta.servlet.ServletException {

        boolean hasJwt = StringUtils.isNotBlank(request.getHeader("Authorization"));
        boolean hasCredential = StringUtils.isNotBlank(request.getHeader("X-Access-Key"));

        User user;
        HttpServletRequest filteredRequest = request;

        if (hasJwt) {
            user = authenticateByJwt(request, response);
        } else if (hasCredential) {
            CachedBodyHttpServletRequest cachedRequest = new CachedBodyHttpServletRequest(request);
            user = authenticateByCredential(cachedRequest, response);
            filteredRequest = cachedRequest;
        } else {
            writeError(response, AuthError.MISSING_AUTH);
            return;
        }

        if (user == null) {
            return;
        }

        if (!user.getEnabled()) {
            writeError(response, AuthError.USER_DISABLED);
            return;
        }

        setAuthentication(user);
        filterChain.doFilter(filteredRequest, response);
    }

    private User authenticateByJwt(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String token = request.getHeader("Authorization");
        if (!tokenService.isValid(token)) {
            writeError(response, AuthError.INVALID_TOKEN);
            return null;
        }
        Long userId = tokenService.parse(token);
        Optional<User> opt = userRepository.findByIdWithRolesAndMenus(userId);
        if (opt.isEmpty()) {
            writeError(response, AuthError.USER_NOT_FOUND);
            return null;
        }
        return opt.get();
    }

    private User authenticateByCredential(HttpServletRequest request, HttpServletResponse response) throws IOException {
        String accessKey = request.getHeader("X-Access-Key");
        String sign = request.getHeader("X-Sign");

        if (StringUtils.isBlank(sign)) {
            writeError(response, AuthError.LACK_REQUIRED_HEADER);
            return null;
        }

        Optional<Credential> opt = credentialRepository.findByAccessKey(accessKey);
        if (opt.isEmpty()) {
            writeError(response, AuthError.ACCESS_KEY_NOT_EXISTED);
            return null;
        }

        Credential credential = opt.get();
        if (!Boolean.TRUE.equals(credential.getEnabled())) {
            writeError(response, AuthError.ACCESS_KEY_DISABLED);
            return null;
        }

        String body = new String(((CachedBodyHttpServletRequest) request).getBody(), StandardCharsets.UTF_8);
        if (!CredentialSignUtil.verify(body, credential.getAccessSecret(), sign)) {
            writeError(response, AuthError.SIGNATURE_VERIFY_FAILURE);
            return null;
        }

        User user = credential.getUser();
        Optional<User> optUser = userRepository.findByIdWithRolesAndMenus(user.getId());
        if (optUser.isEmpty()) {
            writeError(response, AuthError.BIND_USER_NOT_EXISTED);
            return null;
        }
        return optUser.get();
    }
}
