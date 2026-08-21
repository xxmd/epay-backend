package com.example.system.controller;

import com.example.common.domain.Result;
import com.example.crud.controller.EntityCrudController;
import com.example.crud.domain.annotation.PermissionPrefix;
import com.example.system.domain.dto.RoleDto;
import com.example.common.domain.entity.Role;
import com.example.system.domain.query.RoleQueryCondition;
import com.example.system.domain.vo.SimpleRoleVo;
import com.example.system.domain.vo.RoleVo;
import com.example.system.service.RoleService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/role")
@PermissionPrefix("system:role")
@AllArgsConstructor
public class RoleController extends EntityCrudController<Role, RoleQueryCondition, RoleVo, RoleDto> {
    private final RoleService service;

    @GetMapping("/findAll")
    public Result<List<SimpleRoleVo>> findAll() {
        return Result.success(service.findAll());
    }
}
