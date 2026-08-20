package com.example.system.model.query;

import com.example.crud.model.annotation.Condition;
import lombok.Data;

import java.util.Date;
import java.util.List;

@Data
public class UserQueryCondition {
    @Condition(type = Condition.Type.INNER_LIKE)
    private String username;

    @Condition(type = Condition.Type.INNER_LIKE)
    private String nickname;

    @Condition(type = Condition.Type.EQUAL, joinName = "roleSet", propName = "id")
    private Long roleId;

    @Condition(type = Condition.Type.EQUAL)
    private Boolean enabled;

    @Condition(type = Condition.Type.BETWEEN)
    private List<Date> createdDate;
}
