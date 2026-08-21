package com.example.common.model.enums;

import lombok.Getter;

@Getter
public enum ErrorCode {

    EMAIL_EMPTY("邮箱为空"),
    EMAIL_FORMAT_ERROR("邮箱格式错误"),
    EMAIL_REGISTERED("该邮箱已注册"),
    EMAIL_CAPTCHA_ERROR("邮箱验证码未发送或已过期"),
    EMAIL_CAPTCHA_MISMATCH("邮箱验证码不匹配"),
    TWICE_INPUT_PASSWORD_MISMATCH("两次输入密码不匹配"),
    USERNAME_DISABLED("该用户已禁用"),
    USERNAME_OR_PASSWORD_ERROR("用户名或密码错误"),
    UNKNOW_EXCEPTION("未知异常，请联系管理员"),
    FORBIDDEN("无权操作该数据"),
    UNAUTHORIZED("未登录或登录已过期"),
    NO_AVAILABLE_MERCHANT("未找到支持该支付方式的可用商户"),
    ORDER_CANNOT_DELETE("订单不允许删除"),
    ;

    private final String message;

    ErrorCode(String message) {
        this.message = message;
    }
}
