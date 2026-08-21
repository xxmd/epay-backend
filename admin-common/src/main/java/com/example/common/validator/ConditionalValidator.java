package com.example.common.validator;

import com.example.common.domain.annotation.ConditionalNotBlank;
import com.example.common.domain.annotation.ConditionalNotNull;
import com.example.common.domain.annotation.EnableConditionalValidation;
import jakarta.validation.ConstraintValidator;
import jakarta.validation.ConstraintValidatorContext;
import lombok.extern.slf4j.Slf4j;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Method;
import java.util.Arrays;

@Slf4j
public class ConditionalValidator implements ConstraintValidator<EnableConditionalValidation, Object> {

    @Override
    public boolean isValid(Object obj, ConstraintValidatorContext context) {
        if (obj == null) return true;
        Field[] fields = obj.getClass().getDeclaredFields();
        context.disableDefaultConstraintViolation();
        boolean allFiledValid = true;
        for (Field field : fields) {
            boolean singleFiledValid = isFieldValid(obj, field, context);
            if (!singleFiledValid) {
                allFiledValid = false;
            }
        }
        return allFiledValid;
    }

    private boolean isFieldValid(Object obj, Field field, ConstraintValidatorContext context) {
        boolean valid = true;
        field.setAccessible(true);
        try {
            ConditionalNotNull notNull = field.getAnnotation(ConditionalNotNull.class);
            if (notNull != null) {
                valid = checkCondition(obj, field, notNull, ConditionAnnoHandlerFactory.get(notNull), context);
            }
            ConditionalNotBlank notBlank = field.getAnnotation(ConditionalNotBlank.class);
            if (notBlank != null) {
                valid = checkCondition(obj, field, notBlank, ConditionAnnoHandlerFactory.get(notBlank), context);
            }
        } catch (Exception e) {
            log.error("参数条件校验异常", e);
        }
        return valid;
    }

    private boolean checkCondition(Object obj, Field targetField, Annotation annotation, ConditionAnnoHandler conditionAnnoHandler, ConstraintValidatorContext context)
            throws IllegalAccessException, NoSuchFieldException, NoSuchMethodException, InvocationTargetException {
        Method dependsOnMethod = annotation.getClass().getDeclaredMethod("dependsOn");
        String dependFieldName = (String) dependsOnMethod.invoke(annotation);
        Field dependField = obj.getClass().getDeclaredField(dependFieldName);
        dependField.setAccessible(true);
        Object dependFiledValue = dependField.get(obj);
        if (dependFiledValue == null) {
            return true;
        }
        String dependFieldStrValue = dependFiledValue.getClass().isEnum() ? ((Enum<?>) dependFiledValue).name() : dependFiledValue.toString();
        Method valuesMethod = annotation.getClass().getDeclaredMethod("values");
        String[] allowValues = (String[]) valuesMethod.invoke(annotation);
        if (isMeetCheckCriteria(allowValues, dependFieldStrValue)) {
            targetField.setAccessible(true);
            Object targetFiledValue = targetField.get(obj);
            boolean valid = conditionAnnoHandler.isValid(targetFiledValue);
            if (!valid) {
                String tips = conditionAnnoHandler.getTipsOnInValid(targetField, dependField, dependFieldStrValue);
                context.buildConstraintViolationWithTemplate(tips)
                        .addPropertyNode(targetField.getName())
                        .addConstraintViolation();
            }
            return valid;
        }
        return true;
    }

    private boolean isMeetCheckCriteria(String[] allowEqualValues, String dependFieldStrValue) {
        return Arrays.stream(allowEqualValues)
                .anyMatch(s -> s.equalsIgnoreCase(dependFieldStrValue));
    }
}

