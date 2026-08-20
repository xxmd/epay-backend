package com.example.auth.service;

import com.example.common.model.Result;
import com.example.auth.model.dto.EmailRegisterDto;
import com.example.system.model.entity.User;
import com.example.common.model.enums.ErrorCode;
import com.example.system.repository.UserRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.commons.lang3.StringUtils;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import java.util.Optional;

@Service
@Slf4j
@RequiredArgsConstructor
public class RegisterService {
    private static final String EMAIL_REGEX = "^[A-Za-z0-9+_.-]+@[A-Za-z0-9.-]+\\.[A-Za-z]{2,}$";
    private final UserRepository userRepository;
    private final EmailService emailService;
    private final PasswordEncoder passwordEncoder;

    public Result<?> sendEmailCaptcha(String email) {
        if (StringUtils.isBlank(email)) {
            return Result.failure(ErrorCode.EMAIL_EMPTY);
        }
        if (!email.matches(EMAIL_REGEX)) {
            return Result.failure(ErrorCode.EMAIL_FORMAT_ERROR);
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return Result.failure(ErrorCode.EMAIL_REGISTERED);
        }
        try {
            emailService.sendCaptcha(email);
        } catch (Exception e) {
            log.error("注册阶段发送邮箱验证码异常", e);
            return Result.failure(ErrorCode.UNKNOW_EXCEPTION);
        }
        return Result.success();
    }

    public Result<?> byEmail(EmailRegisterDto dto) {
        String email = dto.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            return Result.failure(ErrorCode.EMAIL_REGISTERED);
        }
        String captcha = emailService.getCaptcha(email);
        if (captcha == null) {
            return Result.failure(ErrorCode.EMAIL_CAPTCHA_ERROR);
        }
        if (!dto.getEmailCaptcha().equals(captcha)) {
            return Result.failure(ErrorCode.EMAIL_CAPTCHA_MISMATCH);
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            return Result.failure(ErrorCode.TWICE_INPUT_PASSWORD_MISMATCH);
        }
        User user = creteByEmailRegisterDto(dto);
        return Result.success();
    }

    private User creteByEmailRegisterDto(EmailRegisterDto dto) {
        String email = dto.getEmail();
        User user = new User();
        user.setUsername(email);
        user.setPassword(passwordEncoder.encode(dto.getPassword()));
        user.setNickname(email.substring(0, email.indexOf("@")));
        user.setEmail(email);
        user.setEnabled(true);
        return user;
    }
}
