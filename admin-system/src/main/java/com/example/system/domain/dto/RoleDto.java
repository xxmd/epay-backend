package com.example.system.domain.dto;

import com.example.crud.domain.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleDto extends BaseDto {
    @NotBlank(message = "label角色标签不能为空")
    private String label;

    @NotBlank(message = "value角色值不能为空")
    private String value;

    private Set<Long> menuIdSet;
}
