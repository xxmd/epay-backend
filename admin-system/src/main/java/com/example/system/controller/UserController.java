package com.example.system.controller;

import com.example.crud.controller.EntityCrudController;
import com.example.crud.model.annotation.PermissionPrefix;
import com.example.system.model.dto.UserPasswordDto;
import com.example.system.model.dto.UserDto;
import com.example.system.model.dto.UserProfileDto;
import com.example.system.model.entity.User;
import com.example.system.model.query.UserQueryCondition;
import com.example.system.model.vo.UserVo;
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
    public UserVo getCurrentUser() {
        return userService.getCurrentUser();
    }

    @GetMapping("/findByUsername/{username}")
    public UserVo findByUsername(@PathVariable String username) {
        return userService.findByUsername(username);
    }

    @GetMapping("/findByEmail/{email}")
    public UserVo findByEmail(@PathVariable String email) {
        return userService.findByEmail(email);
    }

    @PostMapping("/updateProfile")
    public void updateProfile(@RequestBody UserProfileDto dto) {
        userService.updateProfile(dto);
    }

    @PostMapping("/updatePassword")
    public void updatePassword(@RequestBody UserPasswordDto dto) {
        userService.updatePassword(dto);
    }
}
