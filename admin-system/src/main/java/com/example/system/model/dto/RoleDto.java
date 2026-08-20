package com.example.system.model.dto;

import com.example.crud.model.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import lombok.Data;
import lombok.Getter;
import lombok.Setter;

import java.util.List;
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
