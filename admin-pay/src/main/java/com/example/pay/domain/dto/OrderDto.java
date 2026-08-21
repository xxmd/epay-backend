package com.example.pay.domain.dto;

import com.example.crud.model.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.math.BigDecimal;

@Data
public class OrderDto extends BaseDto {
    @NotBlank(message = "商品名称不能为空")
    private String productName;

    @NotNull(message = "商品价格不能为空")
    private BigDecimal productPrice;

    @NotNull(message = "商品数量不能为空")
    private Integer productQuantity;

    private String remark;

    @NotNull(message = "应用不能为空")
    private Long applicationId;

    @NotNull(message = "支付方式不能为空")
    private Long methodId;
}
