package com.example.auth.controller;

import com.example.common.domain.Result;
import com.example.common.domain.annotation.Anonymous;
import com.example.auth.domain.dto.EmailRegisterDto;
import com.example.auth.service.RegisterService;
import lombok.AllArgsConstructor;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/register")
@AllArgsConstructor
public class RegisterController {
    private final RegisterService service;

    @Anonymous
    @GetMapping("/sendEmailCaptcha/{email}")
    public Result<Void> sendEmailCaptcha(@PathVariable String email) {
        service.sendEmailCaptcha(email);
        return Result.success();
    }

    @Anonymous
    @PostMapping("/byEmail")
    public Result<Void> byEmail(@RequestBody @Validated EmailRegisterDto dto) {
        service.byEmail(dto);
        return Result.success();
    }
}
