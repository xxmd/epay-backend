package com.example.pay.domain.query;

import com.example.crud.domain.annotation.Condition;
import lombok.Data;

@Data
public class PlatformQueryCondition {
    @Condition(type = Condition.Type.INNER_LIKE)
    private String name;

    @Condition(type = Condition.Type.INNER_LIKE)
    private String contact;

    @Condition(type = Condition.Type.EQUAL)
    private Boolean enabled;
}
