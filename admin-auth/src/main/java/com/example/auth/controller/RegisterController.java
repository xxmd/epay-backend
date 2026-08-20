package com.example.auth.controller;

import com.example.common.model.Result;
import com.example.common.model.annotation.Anonymous;
import com.example.auth.model.dto.EmailRegisterDto;
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
    public Result<?> sendEmailCaptcha(@PathVariable String email) {
        return service.sendEmailCaptcha(email);
    }

    @Anonymous
    @PostMapping("/byEmail")
    public Result<?> byEmail(@RequestBody @Validated EmailRegisterDto dto) {
        return service.byEmail(dto);
    }
}
