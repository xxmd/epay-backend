package com.example.common.exception;

import com.example.common.model.enums.ErrorCode;

public class BusinessException extends RuntimeException {
    public BusinessException(String message) {
        super(message);
    }

    public BusinessException(ErrorCode errorCode) {
        super(errorCode.getMessage());
    }
}
