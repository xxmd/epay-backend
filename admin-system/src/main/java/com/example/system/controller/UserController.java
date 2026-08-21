package com.example.system.controller;

import com.example.common.domain.Result;
import com.example.crud.controller.EntityCrudController;
import com.example.crud.domain.annotation.PermissionPrefix;
import com.example.system.domain.dto.UserPasswordDto;
import com.example.system.domain.dto.UserDto;
import com.example.system.domain.dto.UserProfileDto;
import com.example.common.domain.entity.User;
import com.example.system.domain.query.UserQueryCondition;
import com.example.system.domain.vo.UserVo;
import com.example.system.service.UserService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/system/user")
@PermissionPrefix("system:user")
@AllArgsConstructor
public class UserController extends EntityCrudController<User, UserQueryCondition, UserVo, UserDto> {
    private final UserService userService;

    @GetMapping
    public Result<UserVo> getCurrentUserVo() {
        return Result.success(userService.getCurrentUserVo());
    }

    @GetMapping("/findByUsername/{username}")
    public Result<UserVo> findByUsername(@PathVariable String username) {
        return Result.success(userService.findByUsername(username));
    }

    @GetMapping("/findByEmail/{email}")
    public Result<UserVo> findByEmail(@PathVariable String email) {
        return Result.success(userService.findByEmail(email));
    }

    @PostMapping("/updateProfile")
    public Result<Void> updateProfile(@RequestBody UserProfileDto dto) {
        userService.updateProfile(dto);
        return Result.success();
    }

    @PostMapping("/updatePassword")
    public Result<Void> updatePassword(@RequestBody UserPasswordDto dto) {
        userService.updatePassword(dto);
        return Result.success();
    }
}
