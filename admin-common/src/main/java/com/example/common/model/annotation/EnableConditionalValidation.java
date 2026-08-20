package com.example.common.model.annotation;


import com.example.common.validator.ConditionalValidator;
import jakarta.validation.Constraint;
import jakarta.validation.Payload;

import java.lang.annotation.*;

@Target({ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Constraint(validatedBy = ConditionalValidator.class)
@Documented
public @interface EnableConditionalValidation {

    String message() default "条件校验失败";

    Class<?>[] groups() default {};

    Class<? extends Payload>[] payload() default {};
}
