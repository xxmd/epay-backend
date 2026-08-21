package com.example.auth.security;

import com.example.auth.util.AuthContext;
import lombok.RequiredArgsConstructor;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.domain.AuditorAware;
import org.springframework.stereotype.Component;

import java.util.Optional;

@Component
@RequiredArgsConstructor
public class SecurityAuditorAware implements AuditorAware<String> {
    private final AuthContext authContext;

    @Override
    public Optional<String> getCurrentAuditor() {
        String currentUsername = authContext.getCurrentUsername();
        if (StringUtils.isBlank(currentUsername)) {
            currentUsername = "system";
        }
        return Optional.of(currentUsername);
    }
}