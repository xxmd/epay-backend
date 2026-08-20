package com.example.auth.controller;

import com.example.common.model.Result;
import com.example.common.model.annotation.Anonymous;
import com.example.auth.model.dto.UserLoginDto;
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
        return service.login(dto);
    }

    @GetMapping("/logout")
    public void logout(HttpServletRequest request) {
        service.logout(request);
    }
}
