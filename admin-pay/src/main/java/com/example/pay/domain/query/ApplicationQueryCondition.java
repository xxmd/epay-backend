package com.example.pay.domain.query;

import com.example.crud.model.annotation.Condition;
import com.example.pay.domain.enums.Platform;
import lombok.Data;

@Data
public class ApplicationQueryCondition {

    @Condition(type = Condition.Type.INNER_LIKE)
    private String name;

    @Condition(type = Condition.Type.EQUAL)
    private Platform platform;

    @Condition(type = Condition.Type.EQUAL)
    private Boolean enabled;
}
