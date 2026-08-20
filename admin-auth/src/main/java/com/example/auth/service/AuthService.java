package com.example.auth.service;

import com.example.common.model.Result;
import com.example.auth.model.dto.UserLoginDto;
import com.example.system.model.entity.User;
import com.example.common.model.enums.ErrorCode;
import com.example.system.repository.UserRepository;
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

    public Result<?> login(UserLoginDto dto) {
        Optional<User> optionalUser = userRepository.findByUsername(dto.getUsername());
        if (optionalUser.isEmpty()) {
            return Result.failure(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        User dbUser = optionalUser.get();
        if (!dbUser.getEnabled()) {
            return Result.failure(ErrorCode.USERNAME_DISABLED);
        }
        if (!passwordEncoder.matches(dto.getPassword(), dbUser.getPassword())) {
            return Result.failure(ErrorCode.USERNAME_OR_PASSWORD_ERROR);
        }
        String token = tokenService.create(dbUser);
        return Result.success(Map.of("token", token));
    }

    public void logout(HttpServletRequest request) {
        String token = request.getHeader("Authorization");
        tokenService.delete(token);
    }
}
