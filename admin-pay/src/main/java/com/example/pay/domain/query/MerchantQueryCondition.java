package com.example.pay.domain.query;

import com.example.crud.domain.annotation.Condition;
import lombok.Data;

@Data
public class MerchantQueryCondition {
    @Condition(type = Condition.Type.EQUAL)
    private Integer merchantId;

    @Condition(type = Condition.Type.EQUAL)
    private Boolean enabled;

    @Condition(type = Condition.Type.EQUAL, joinName = "platform", propName = "id")
    private Long platformId;
}
