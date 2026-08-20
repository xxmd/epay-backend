package com.example.common.model.annotation;

import jakarta.validation.Payload;
import java.lang.annotation.*;

@Target({ElementType.FIELD})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface ConditionalNotBlank {

    String message() default "{field}不能为空";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};

    String dependsOn();

    String[] values() default {};
}
