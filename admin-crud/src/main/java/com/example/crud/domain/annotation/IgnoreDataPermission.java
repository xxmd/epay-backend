package com.example.crud.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 标注在方法或类上，跳过数据权限控制。
 * <p>
 * 优先级高于 {@link RequireCreatedBy}：当类上标注了 @RequireCreatedBy，
 * 但某个方法（如支付回调）不需要校验 createdBy 时，在方法上标注本注解即可豁免。
 * 方法级注解优先于类级注解。
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
public @interface IgnoreDataPermission {
}
