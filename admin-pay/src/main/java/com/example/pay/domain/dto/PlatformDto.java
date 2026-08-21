package com.example.pay.domain.dto;

import com.example.crud.domain.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

@Data
public class PlatformDto extends BaseDto {
    @NotBlank(message = "平台名称不能为空")
    private String name;

    @NotBlank(message = "平台域名不能为空")
    private String domainName;

    @NotBlank(message = "平台联系方式不能为空")
    private String contact;

    private Integer sort;

    @NotNull(message = "平台是否启用不能为空")
    private Boolean enabled;

    private String remark;
}
