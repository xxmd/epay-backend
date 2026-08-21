package com.example.pay.domain.query;

import com.example.crud.domain.annotation.Condition;
import com.example.pay.domain.enums.PayStatus;
import lombok.Data;

@Data
public class OrderQueryCondition {
    @Condition(type = Condition.Type.INNER_LIKE)
    private String orderNumber;

    @Condition(type = Condition.Type.INNER_LIKE)
    private String productName;

    @Condition(type = Condition.Type.EQUAL)
    private PayStatus payStatus;

//    @Condition(type = Condition.Type.EQUAL, joinName = "merchant", propName = "id")
//    private Long merchantId;

    @Condition(type = Condition.Type.EQUAL, joinName = "application", propName = "id")
    private Long applicationId;

    @Condition(type = Condition.Type.EQUAL, joinName = "method", propName = "id")
    private Long methodId;
}
