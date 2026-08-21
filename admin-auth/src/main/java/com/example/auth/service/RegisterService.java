package com.example.auth.service;

import com.example.auth.domain.dto.EmailRegisterDto;
import com.example.auth.domain.enums.AuthError;
import com.example.common.exception.BusinessException;
import com.example.common.domain.entity.User;
import com.example.common.repository.UserRepository;
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

    public void sendEmailCaptcha(String email) {
        if (StringUtils.isBlank(email)) {
            throw new BusinessException(AuthError.REGISTER_EMAIL_EMPTY);
        }
        if (!email.matches(EMAIL_REGEX)) {
            throw new BusinessException(AuthError.REGISTER_EMAIL_FORMAT_ERROR);
        }
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new BusinessException(AuthError.REGISTER_EMAIL_USED);
        }
        try {
            emailService.sendCaptcha(email);
        } catch (Exception e) {
            log.error("注册阶段发送邮箱验证码异常", e);
            throw new BusinessException(AuthError.REGISTER_EMAIL_CAPTCHA_SEND_FAILURE);
        }
    }

    public void byEmail(EmailRegisterDto dto) {
        String email = dto.getEmail();
        Optional<User> optionalUser = userRepository.findByEmail(email);
        if (optionalUser.isPresent()) {
            throw new BusinessException(AuthError.REGISTER_EMAIL_USED);
        }
        String captcha = emailService.getCaptcha(email);
        if (captcha == null) {
            throw new BusinessException(AuthError.REGISTER_EMAIL_CAPTCHA_UNSENT_OR_EXPIRED);
        }
        if (!dto.getEmailCaptcha().equals(captcha)) {
            throw new BusinessException(AuthError.REGISTER_EMAIL_CAPTCHA_MISMATCH);
        }
        if (!dto.getPassword().equals(dto.getConfirmPassword())) {
            throw new BusinessException(AuthError.REGISTER_TWICE_INPUT_PASSWORD_MISMATCH);
        }
        User user = creteByEmailRegisterDto(dto);
        userRepository.save(user);
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
