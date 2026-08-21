package com.example.system.domain.enums;

import com.example.common.domain.enums.ReadableError;

public enum SystemError implements ReadableError {
    USERNAME_EXISTED("用户名已存在"),
    SRC_PASSWORD_MISMATCH("原密码不匹配"),
    TWICE_INPUT_PASSWORD_MISMATCH("新密码和确认密码不一致"),
    ;

    SystemError(String message) {
        this.message = message;
    }

    private String message;

    @Override
    public String getReason() {
        return this.name();
    }

    @Override
    public String getMessage() {
        return message;
    }
}
