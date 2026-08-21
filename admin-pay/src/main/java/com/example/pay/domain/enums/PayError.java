package com.example.pay.domain.enums;

import com.example.common.domain.enums.ReadableError;

public enum PayError implements ReadableError {
    ORDER_NOT_EXISTED("订单不存在"),
    ;

    PayError(String message) {
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
