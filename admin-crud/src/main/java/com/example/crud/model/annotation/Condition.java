package com.example.crud.model.annotation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Target(ElementType.FIELD)
@Retention(RetentionPolicy.RUNTIME)
public @interface Condition {
    Type type() default Type.EQUAL;

    String joinName() default "";

    String propName() default "";

    boolean ignoreNull() default true;

    enum Type {
        EQUAL,
        INNER_LIKE,
        BETWEEN
    }
}
