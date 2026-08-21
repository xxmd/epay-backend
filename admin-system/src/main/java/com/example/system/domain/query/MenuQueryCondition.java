package com.example.system.domain.query;

import com.example.crud.domain.annotation.Condition;
import lombok.Data;

@Data
public class MenuQueryCondition {
    @Condition(type = Condition.Type.EQUAL, ignoreNull = false)
    private Integer parentId;
}
