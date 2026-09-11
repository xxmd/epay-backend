package com.example.common.domain;

import com.example.common.domain.enums.ReadableError;
import lombok.Data;

@Data
public class Result<T> {
    private int code;
    private boolean success;
    private String reason;
    private String message;
    private T data;

    public static <T> Result<T> success(T data) {
        Result<T> result = new Result<>();
        result.code = 200;
        result.success = true;
        result.data = data;
        return result;
    }

    public static <T> Result<T> success() {
        return success(null);
    }

    public static <T> Result<T> failure(int code, String reason, String message) {
        Result<T> result = new Result<>();
        result.code = code;
        result.success = false;
        result.reason = reason;
        result.message = message;
        return result;
    }

    public static <T> Result<T> failure(String reason, String message) {
        return failure(0, reason, message);
    }

    public static <T> Result<T> failure(ReadableError readableError) {
        return failure(readableError.getReason(), readableError.getMessage());
    }
}
