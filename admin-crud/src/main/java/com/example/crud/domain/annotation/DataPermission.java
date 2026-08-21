package com.example.crud.domain.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * 启用数据权限：用户只能操作自己创建的数据。
 * 标注在 Service 类上，自动在查询时过滤 createdBy，在更新/删除时校验所有权。
 */
@Target(ElementType.TYPE)
@Retention(RetentionPolicy.RUNTIME)
public @interface DataPermission {
}
