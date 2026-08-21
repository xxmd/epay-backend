package com.example.auth.util;

import com.example.auth.domain.enums.AuthError;
import com.example.common.exception.BusinessException;
import com.example.common.domain.entity.User;
import com.example.common.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class AuthContext {
    private final UserRepository userRepository;

    public String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            return "system";
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            return (String) authenticationToken.getPrincipal();
        }
        throw new BusinessException(AuthError.GET_CURRENT_USER_FAILURE);
    }

    public User getCurrentUser() {
        String username = getCurrentUsername();
        Optional<User> optionalUser = userRepository.findByUsername(username);
        if (optionalUser.isEmpty()) {
            throw new BusinessException(AuthError.GET_CURRENT_USER_FAILURE);
        }
        return optionalUser.get();
    }
}
