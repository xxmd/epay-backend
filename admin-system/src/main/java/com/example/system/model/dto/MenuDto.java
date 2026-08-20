package com.example.system.model.dto;

import com.example.common.model.annotation.ConditionalNotBlank;
import com.example.common.model.annotation.EnableConditionalValidation;
import com.example.crud.model.dto.BaseDto;
import com.example.system.model.enums.MenuType;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@EnableConditionalValidation
public class MenuDto extends BaseDto {
    private Long parentId;

    @NotNull(message = "菜单类型不能为空")
    private MenuType type;

    @NotBlank(message = "菜单标题不能为空")
    private String title;

    @ConditionalNotBlank(dependsOn = "type", values = {"MENU"})
    private String path;

    @ConditionalNotBlank(dependsOn = "type", values = {"MENU"})
    private String component;

    @ConditionalNotBlank(dependsOn = "type", values = {"MENU", "BUTTON"})
    private String permission;

    private Integer sort;

    @NotNull(message = "菜单是否隐藏不能为空")
    private Boolean hidden;
}
