package com.example.auth.security;

import com.example.auth.domain.enums.AuthError;
import com.example.common.domain.Result;
import com.example.common.domain.entity.Menu;
import com.example.common.domain.entity.Role;
import com.example.common.domain.entity.User;
import jakarta.servlet.http.HttpServletResponse;
import org.apache.commons.lang3.StringUtils;
import org.springframework.http.MediaType;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.authority.SimpleGrantedAuthority;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.web.filter.OncePerRequestFilter;
import tools.jackson.databind.ObjectMapper;

import java.io.IOException;
import java.util.Collections;
import java.util.List;
import java.util.Set;
import java.util.stream.Collectors;

public abstract class AbstractUserAuthFilter extends OncePerRequestFilter {

    protected final ObjectMapper objectMapper;

    protected AbstractUserAuthFilter(ObjectMapper objectMapper) {
        this.objectMapper = objectMapper;
    }

    protected void setAuthentication(User user) {
        Set<String> permissions = extractPermissions(user);
        List<SimpleGrantedAuthority> authorities = permissions.stream()
                .map(SimpleGrantedAuthority::new)
                .collect(Collectors.toList());
        UsernamePasswordAuthenticationToken auth = new UsernamePasswordAuthenticationToken(
                user.getUsername(), user.getPassword(), authorities);
        SecurityContextHolder.getContext().setAuthentication(auth);
    }

    protected Set<String> extractPermissions(User user) {
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

    protected void writeError(HttpServletResponse response, AuthError error) throws IOException {
        response.setStatus(HttpServletResponse.SC_UNAUTHORIZED);
        response.setContentType(MediaType.APPLICATION_JSON_VALUE);
        response.setCharacterEncoding("UTF-8");
        response.getWriter().write(objectMapper.writeValueAsString(
                Result.failure(401, error.getReason(), error.getMessage())));
    }
}
