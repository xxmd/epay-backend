package com.example.common.domain.enums;

import lombok.Getter;

@Getter
public enum CommonError implements ReadableError {
    FORBIDDEN("无权操作该数据"),
    UNAUTHORIZED("未登录或登录已过期"),
    NO_AVAILABLE_MERCHANT("未找到支持该支付方式的可用商户"),
    ORDER_CANNOT_DELETE("订单不允许删除"),
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
