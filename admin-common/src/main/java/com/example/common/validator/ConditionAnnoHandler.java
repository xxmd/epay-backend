package com.example.common.validator;

import java.lang.reflect.Field;


public interface ConditionAnnoHandler {
    Boolean isValid(Object targetFiledValue);

    String getTipsOnInValid(Field targetField, Field dependFiled, String dependFiledValue);
}