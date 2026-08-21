package com.example.crud.domain.enums;

import com.example.common.domain.enums.ReadableError;

public enum CrudError implements ReadableError {
    ID_NOT_NULL_WHEN_CREATE("创建时id必须为空"),
    ID_IS_NULL_WHEN_UPDATE("创建时id必须为空"),
    AUTHENTICATION_IS_NULL("认证信息为空"),
    GET_CURRENT_USER_FAILURE("获取当前用户失败"),
    ;

    CrudError(String message) {
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
