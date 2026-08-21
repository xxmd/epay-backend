package com.example.web.domain.enums;

import com.example.common.domain.enums.ReadableError;

public enum WebError implements ReadableError {
    METHOD_ARGUMENT_NOT_VALID(),
    UNKNOWN_EXCEPTION("未知异常"),
    ;

    WebError() {
    }

    WebError(String message) {
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