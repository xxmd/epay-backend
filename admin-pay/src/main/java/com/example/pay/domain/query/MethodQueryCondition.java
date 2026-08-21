package com.example.pay.domain.query;

import com.example.crud.domain.annotation.Condition;
import lombok.Data;

@Data
public class MethodQueryCondition {
    @Condition(type = Condition.Type.INNER_LIKE)
    private String label;

    @Condition(type = Condition.Type.EQUAL)
    private Boolean enabled;
}
