package com.example.common.domain.annotation;

import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConditionalNotNull {

    String message() default "{field}不能为null";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dependsOn();

    String[] values();
}
