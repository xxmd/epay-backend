package com.example.pay.domain.dto;

import com.example.crud.domain.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Data;

import java.util.Set;

@Data
public class MerchantDto extends BaseDto {
    @NotNull(message = "商户id不能为空")
    private Integer merchantId;

    @NotBlank(message = "商户md5私钥不能为空")
    private String md5SecretKey;

    private Integer sort;

    @NotNull(message = "商户是否启用不能为空")
    private Boolean enabled;

    private String remark;

    @NotNull(message = "商户所属平台id不能为空")
    private Long platformId;

    private Set<Long> methodIdSet;
}
