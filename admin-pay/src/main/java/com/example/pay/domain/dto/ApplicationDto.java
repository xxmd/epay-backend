package com.example.pay.domain.dto;

import com.example.crud.domain.dto.BaseDto;
import com.example.pay.domain.enums.Platform;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class ApplicationDto extends BaseDto {
    @NotNull(message = "应用图标不能为空")
    private Long iconFileId;

    @NotBlank(message = "应用名称不能为空")
    private String name;

    @NotNull(message = "应用平台不能为空")
    private Platform platform;

    @NotNull(message = "应用是否启用不能为空")
    private Boolean enabled;

    private String remark;
}
