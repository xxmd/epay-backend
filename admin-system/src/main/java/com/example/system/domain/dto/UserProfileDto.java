package com.example.system.domain.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserProfileDto {
    @NotBlank(message = "username不能为空")
    private String username;

    @NotBlank(message = "nickname昵称不能为空")
    private String nickname;
}
