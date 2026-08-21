package com.example.pay.domain.dto;

import com.example.crud.domain.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class MethodDto extends BaseDto {
    @NotBlank(message = "支付方式名称不能为空")
    private String label;

    @NotBlank(message = "支付方式值不能为空")
    private String value;

    @NotNull(message = "支付方式是否启用不能为空")
    private Boolean enabled;
}
