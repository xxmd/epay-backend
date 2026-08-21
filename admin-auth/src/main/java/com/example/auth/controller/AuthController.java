package com.example.auth.controller;

import com.example.common.domain.Result;
import com.example.common.domain.annotation.Anonymous;
import com.example.auth.domain.dto.UserLoginDto;
import com.example.auth.service.AuthService;
import jakarta.servlet.http.HttpServletRequest;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
@AllArgsConstructor
public class AuthController {
    private final AuthService service;

    @Anonymous
    @PostMapping("/login")
    public Result<?> login(@Validated @RequestBody UserLoginDto dto) {
        return Result.success(service.login(dto));
    }

    @GetMapping("/logout")
    public Result<Void> logout(HttpServletRequest request) {
        service.logout(request);
        return Result.success();
    }
}
