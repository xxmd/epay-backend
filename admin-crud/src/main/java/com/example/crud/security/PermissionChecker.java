package com.example.crud.security;

import com.example.crud.model.annotation.PermissionPrefix;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.GrantedAuthority;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.Set;
import java.util.stream.Collectors;

@Component("permissionChecker")
public class PermissionChecker {
    public boolean hasPermission(Authentication authentication, Object target, String[] permissionArr) {
        if (authentication == null || !authentication.isAuthenticated() || target == null) {
            return false;
        }
        if (permissionArr == null || permissionArr.length == 0) {
            return true;
        }
        String prefixAnnotationValue = "";
        PermissionPrefix prefixAnnotation = target.getClass().getAnnotation(PermissionPrefix.class);
        if (prefixAnnotation != null) {
            prefixAnnotationValue = prefixAnnotation.value();
        }
        String prefix = StringUtils.isNotBlank(prefixAnnotationValue) && !prefixAnnotationValue.endsWith(":")
                ? prefixAnnotationValue + ":"
                : prefixAnnotationValue;
        String[] requiredPermissions = Arrays.stream(permissionArr)
                .filter(StringUtils::isNotBlank)
                .map(permission -> prefix + permission)
                .toArray(String[]::new);
        if (requiredPermissions.length == 0) {
            return true;
        }
        Set<String> ownedPermissions = authentication.getAuthorities().stream()
                .map(GrantedAuthority::getAuthority)
                .filter(StringUtils::isNotBlank)
                .collect(Collectors.toSet());
        return Arrays.stream(requiredPermissions)
                .anyMatch(ownedPermissions::contains);
    }
}