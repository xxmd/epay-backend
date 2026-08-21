package com.example.auth.service;

import com.example.common.domain.entity.User;
import com.example.auth.util.JwtUtil;
import io.jsonwebtoken.Claims;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@AllArgsConstructor
public class TokenService {
    private final StringRedisTemplate redisTemplate;

    private String getKey(Long userId) {
        return "user_token_" + userId;
    }

    public String create(User user) {
        Long userId = user.getId();
        int expireDays = 7;
        TimeUnit expireTimeUnit = TimeUnit.DAYS;
        String token = JwtUtil.create(String.valueOf(userId), expireDays, expireTimeUnit);
        String key = getKey(userId);
        redisTemplate.opsForValue().set(key, token, expireDays, expireTimeUnit);
        return token;
    }

    public Long parse(String token) {
        try {
            if (StringUtils.isBlank(token)) {
                throw new IllegalArgumentException("token is empty");
            }
            Claims claims = JwtUtil.parse(token);
            String subject = claims.getSubject();
            return Long.parseLong(subject);
        } catch (Exception e) {
            log.error("token parse exception", e);
        }
        return null;
    }

    public void delete(String token) {
        if (StringUtils.isBlank(token)) {
            return;
        }
        Long userId = parse(token);
        if (userId == null) {
            return;
        }
        redisTemplate.delete(getKey(userId));
    }

    public boolean isValid(String token) {
        Long userId = parse(token);
        if (userId == null) {
            return false;
        }
        return redisTemplate.hasKey(getKey(userId));
    }
}
