package com.example.system.domain.dto;

import com.example.crud.domain.dto.BaseDto;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserDto extends BaseDto {
    @NotBlank(message = "username不能为空")
    private String username;

    @NotBlank(message = "nickname昵称不能为空")
    private String nickname;

    @NotBlank(message = "email邮箱不能为空")
    private String email;

    @NotNull(message = "enabled是否启用不能为空")
    private Boolean enabled;

    private Set<Long> roleIdSet;
}
