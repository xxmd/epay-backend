package com.example.common.exception;

import com.example.common.domain.enums.ReadableError;
import lombok.Getter;

@Getter
public class BusinessException extends RuntimeException {
    private final String reason;

    public BusinessException(ReadableError readableError) {
        super(readableError.getMessage());
        this.reason = readableError.getReason();
    }
}
