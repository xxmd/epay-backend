package com.example.auth.security;

import com.example.system.model.entity.Menu;
import com.example.system.model.entity.Role;
import com.example.system.model.entity.User;
import com.example.system.repository.UserRepository;
import com.example.auth.service.TokenService;
import jakarta.servlet.FilterChain;
import jakarta.servlet.ServletException;
import jakarta.servlet.http.HttpServletRequest;
import jakarta.servlet.http.HttpServletResponse;
import lombok.AllArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.*;
import java.util.stream.Collectors;

@Component
@AllArgsConstructor
public class JwtAuthenticationFilter extends OncePerRequestFilter {
    private final TokenService tokenService;
    private final UserRepository userRepository;
    private final ObjectMapper objectMapper;

    @Override
    protected void doFilterInternal(HttpServletRequest request, HttpServletResponse response, FilterChain filterChain) throws ServletException, IOException {
        String token = request.getHeader("Authorization");
        if (StringUtils.isBlank(token)) {
            writeErrorResponse(response, "token is empty");
            return;
        }
        if (!tokenService.isValid(token)) {
            writeErrorResponse(response, "token is invalid, maybe don't contains user id subject or expired");
            return;
        }
        Long userId = tokenService.parse(token);
        Optional<User> optionalUser = userRepository.findByIdWithRolesAndMenus(userId);
        if (optionalUser.isEmpty()) {
            writeErrorResponse(response, "token subject user don't existed");
            return;
        }
        User user = optionalUser.get();
        if (!user.getEnabled()) {
            writeErrorResponse(response, "token subject user is disabled");
            return;
        }
        Set<String> permissions = extractPermissions(user);
        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(user, token, authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
        filterChain.doFilter(request, response);
    }

    private void writeErrorResponse(HttpServletResponse response, String message) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        Map<String, Object> body = new HashMap<>();
        body.put("message", message);
        response.getWriter().write(objectMapper.writeValueAsString(body));
    }

    private Set<String> extractPermissions(User user) {
        Set<Role> roleSet = user.getRoleSet();
        if (roleSet == null || roleSet.isEmpty()) {
            return Collections.emptySet();
        }
        return roleSet.stream()
                .filter(role -> role.getMenuSet() != null)
                .flatMap(role -> role.getMenuSet().stream())
                .map(Menu::getPermission)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
    }
}