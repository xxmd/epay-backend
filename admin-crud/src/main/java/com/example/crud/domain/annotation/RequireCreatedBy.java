package com.example.crud.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在方法或类上，限制只能操作 createdBy == 当前用户 的数据。
 * <p>
 * 查询时自动过滤 createdBy，更新/删除时自动校验所有权。
 * 标注在类上时，所有方法均生效；标注在方法上时，仅该方法生效。
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface RequireCreatedBy {
}
