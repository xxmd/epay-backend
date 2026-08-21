package com.example.common.domain.enums;

import lombok.Getter;

@Getter
public enum CommonError implements ReadableError {
    FORBIDDEN("无权操作该数据"),
    ;

    private final String message;

    CommonError(String message) {
        this.message = message;
    }

    @Override
    public String getReason() {
        return name();
    }
}
