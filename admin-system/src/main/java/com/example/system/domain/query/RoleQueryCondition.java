package com.example.system.domain.query;

import com.example.crud.domain.annotation.Condition;
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
