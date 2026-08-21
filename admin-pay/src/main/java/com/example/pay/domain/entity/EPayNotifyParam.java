package com.example.pay.domain.entity;

import com.fasterxml.jackson.annotation.JsonProperty;
import lombok.Data;

@Data
public class EPayNotifyParam {
    private Integer pid;

    @JsonProperty(value = "trade_no")
    private String tradeNo;

    @JsonProperty(value = "out_trade_no")
    private String outTradeNo;

    private String type;

    private String name;

    private String money;

    @JsonProperty(value = "trade_status")
    private String tradeStatus;

    private String sign;

    @JsonProperty(value = "sign_type")
    private String signType;

    public boolean isValid() {
        return pid != null
                && isNotBlank(tradeNo)
                && isNotBlank(outTradeNo)
                && isNotBlank(type)
                && isNotBlank(name)
                && isNotBlank(money)
                && isNotBlank(tradeStatus)
                && isNotBlank(sign)
                && isNotBlank(signType);
    }

    private static boolean isNotBlank(String str) {
        return str != null && !str.isBlank();
    }
}
