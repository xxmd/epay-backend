package com.example.auth.util;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;

import javax.crypto.SecretKey;
import java.nio.charset.StandardCharsets;
import java.util.Date;
import java.util.concurrent.TimeUnit;

public class JwtUtil {
    private static final String SECRET_KEY_STRING = "your-256-bit-secret-your-256-bit-secret";
    private static final SecretKey SECRET_KEY = Keys.hmacShaKeyFor(SECRET_KEY_STRING.getBytes(StandardCharsets.UTF_8));

    public static String create(String subject, int expireValue, TimeUnit expireUnit) {
        Date now = new Date();
        Date expiredDate = new Date(now.getTime() + expireUnit.toMillis(expireValue));

        return Jwts.builder()
                .subject(subject)
                .issuedAt(now)
                .expiration(expiredDate)
                .signWith(SECRET_KEY)
                .compact();
    }

    public static Claims parse(String token) {
        return Jwts.parser()
                .verifyWith(SECRET_KEY)
                .build()
                .parseSignedClaims(token)
                .getPayload();
    }
}
