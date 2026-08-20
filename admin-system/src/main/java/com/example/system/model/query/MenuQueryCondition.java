package com.example.system.model.query;

import com.example.crud.model.annotation.Condition;
import lombok.Data;

@Data
public class MenuQueryCondition {
    @Condition(type = Condition.Type.EQUAL, ignoreNull = false)
    private Integer parentId;
}
