package com.example.system.model.query;

import com.example.crud.model.annotation.Condition;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class RoleQueryCondition {
    @Condition(type = Condition.Type.INNER_LIKE)
    private String label;

    @Condition(type = Condition.Type.BETWEEN)
    private List<Date> createdDate;
}
