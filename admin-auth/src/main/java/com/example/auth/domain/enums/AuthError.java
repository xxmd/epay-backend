package com.example.auth.domain.enums;

import com.example.common.domain.enums.ReadableError;

public enum AuthError implements ReadableError {
    LOGIN_USERNAME_OR_PASSWORD_ERROR("用户名或密码错误"),
    LOGIN_USERNAME_DISABLED("该用户已禁用"),

    REGISTER_EMAIL_EMPTY("邮箱为空"),
    REGISTER_EMAIL_FORMAT_ERROR("邮箱格式错误"),
    REGISTER_EMAIL_USED("该邮箱已注册"),
    REGISTER_EMAIL_CAPTCHA_SEND_FAILURE("邮箱验证码发送失败"),
    REGISTER_EMAIL_CAPTCHA_UNSENT_OR_EXPIRED("邮箱验证码未发送或已过期"),
    REGISTER_EMAIL_CAPTCHA_MISMATCH("邮箱验证码不匹配"),
    REGISTER_TWICE_INPUT_PASSWORD_MISMATCH("两次输入密码不匹配"),

    AUTHENTICATION_IS_NULL("认证信息为空"),
    GET_CURRENT_USER_FAILURE("获取当前用户失败"),
    ;

    AuthError(String message) {
        this.message = message;
    }

    private final String message;

    @Override
    public String getReason() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
