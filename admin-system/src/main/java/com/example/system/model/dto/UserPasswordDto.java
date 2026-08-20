package com.example.system.model.dto;

import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class UserPasswordDto {
    @NotBlank(message = "srcPassword原密码不能为空")
    private String srcPassword;
    @NotBlank(message = "newPassword新密码不能为空")
    private String newPassword;
    @NotBlank(message = "confirmPassword确认密码不能为空")
    private String confirmPassword;
}
