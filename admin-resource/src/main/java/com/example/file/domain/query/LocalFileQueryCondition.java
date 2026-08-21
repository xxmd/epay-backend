package com.example.file.domain.query;

import com.example.crud.domain.annotation.Condition;
import lombok.Data;

@Data
public class LocalFileQueryCondition {
    @Condition(type = Condition.Type.INNER_LIKE)
    private String name;
}
