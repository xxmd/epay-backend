package com.example.auth.service;

import com.example.auth.domain.enums.AuthError;
import com.example.common.exception.BusinessException;
import com.example.auth.domain.dto.UserLoginDto;
import com.example.common.domain.entity.User;
import com.example.common.repository.UserRepository;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Map;
import java.util.Optional;

@Service
@Slf4j
@AllArgsConstructor
public class AuthService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final TokenService tokenService;

    public Map<String, String> login(UserLoginDto dto) {
        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());
        if (optionalUser.isEmpty()) {
            throw new BusinessException(AuthError.LOGIN_USERNAME_OR_PASSWORD_ERROR);
        }
        User dbUser = optionalUser.get();
        if (!dbUser.getEnabled()) {
            throw new BusinessException(AuthError.LOGIN_USERNAME_DISABLED);
        }
        if (!passwordEncoder.matches(dto.getPassword(), dbUser.getPassword())) {
            throw new BusinessException(AuthError.LOGIN_USERNAME_OR_PASSWORD_ERROR);
        }
        String token = tokenService.create(dbUser);
        return Map.of("token", token);
    }

    public void logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        tokenService.delete(token);
    }
}
