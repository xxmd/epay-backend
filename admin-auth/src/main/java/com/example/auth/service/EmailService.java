package com.example.auth.service;

import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.MimeMessage;

import java.security.SecureRandom;
import java.util.concurrent.TimeUnit;

@Service
@Slf4j
@RequiredArgsConstructor
public class EmailService {
    private static final String CAPTCHA_KEY_PREFIX = "email_captcha:";
    private static final int CAPTCHA_LENGTH = 6;
    private static final int CAPTCHA_EXPIRE_MINUTES = 30;

    private final StringRedisTemplate redisTemplate;
    private final JavaMailSender mailSender;
    @Value("${spring.mail.username}")
    private String mailUsername;

    public void sendCaptcha(String email) {
        String code = generateCaptcha();
        String key = getKey(email);
        redisTemplate.opsForValue().set(key, code, CAPTCHA_EXPIRE_MINUTES, TimeUnit.MINUTES);
        doSend(email, code);
    }

    private String getKey(String email) {
        return CAPTCHA_KEY_PREFIX + email;
    }

    public String getCaptcha(String email) {
        String key = getKey(email);
        return redisTemplate.opsForValue().get(key);
    }

    private String generateCaptcha() {
        SecureRandom random = new SecureRandom();
        StringBuilder sb = new StringBuilder(CAPTCHA_LENGTH);
        for (int i = 0; i < CAPTCHA_LENGTH; i++) {
            sb.append(random.nextInt(10));
        }
        return sb.toString();
    }

    private void doSend(String email, String code) {
        try {
            MimeMessage message = mailSender.createMimeMessage();
            MimeMessageHelper helper = new MimeMessageHelper(message, true, "UTF-8");
            helper.setFrom(mailUsername);
            helper.setTo(email);
            helper.setSubject("佛跳墙");
            String content = "<h3>您的验证码为：</h3>"
                    + "<h1 style='color:#1890ff;letter-spacing:4px;'>" + code + "</h1>"
                    + "<p>验证码 " + CAPTCHA_EXPIRE_MINUTES + " 分钟内有效，请勿泄露给他人。</p>";
            helper.setText(content, true);
            mailSender.send(message);
            log.info("验证码已发送至邮箱: {}", email);
        } catch (MessagingException e) {
            log.error("发送验证码邮件失败, email: {}", email, e);
            throw new RuntimeException("发送验证码失败", e);
        }
    }
}
