package com.example.common.validator;


import com.example.common.model.annotation.ConditionalNotBlank;
import com.example.common.model.annotation.ConditionalNotNull;
import org.apache.commons.lang3.StringUtils;

import java.lang.annotation.Annotation;
import java.lang.reflect.Field;

public class ConditionAnnoHandlerFactory {
    public static ConditionAnnoHandler get(Annotation annotation) {
        if (annotation instanceof ConditionalNotNull) {
            return new NotNullAnnoHandler();
        } else if (annotation instanceof ConditionalNotBlank) {
            return new NotBlankAnnoHandler();
        }
        return null;
    }

    private static class NotNullAnnoHandler implements ConditionAnnoHandler {
        @Override
        public Boolean isValid(Object filedValue) {
            return filedValue != null;
        }

        public String getTipsOnInValid(Field targetField, Field dependFiled, String dependFiledValue) {
            return String.format("当%s字段为%s时，%s字段不能为null", dependFiled.getName(), dependFiledValue, targetField.getName());
        }
    }

    private static class NotBlankAnnoHandler implements ConditionAnnoHandler {
        @Override
        public Boolean isValid(Object filedValue) {
            return filedValue instanceof String && StringUtils.isNotBlank((String) filedValue);
        }

        public String getTipsOnInValid(Field targetField, Field dependFiled, String dependFiledValue) {
            return String.format("当%s字段为%s时，%s字段不能为空", dependFiled.getName(), dependFiledValue, targetField.getName());
        }
    }
}
