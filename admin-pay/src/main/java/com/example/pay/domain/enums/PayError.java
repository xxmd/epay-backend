package com.example.pay.domain.enums;

import com.example.common.domain.enums.ReadableError;

public enum PayError implements ReadableError {
    ORDER_NOT_EXISTED("订单不存在"),
    NO_AVAILABLE_MERCHANT("暂无可用商户，无法创建订单，请联系管理员"),
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
