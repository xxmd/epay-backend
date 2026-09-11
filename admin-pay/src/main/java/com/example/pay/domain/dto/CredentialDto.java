package com.example.pay.domain.dto;

import com.example.crud.domain.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class CredentialDto extends BaseDto {
    @NotBlank(message = "凭证名称不能为空")
    private String name;

    @NotNull(message = "是否启用不能为空")
    private Boolean enabled;

    private String remark;
}
