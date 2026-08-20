package com.example.system.controller;

import com.example.crud.controller.EntityCrudController;
import com.example.crud.model.annotation.PermissionPrefix;
import com.example.system.model.dto.RoleDto;
import com.example.system.model.entity.Role;
import com.example.system.model.query.RoleQueryCondition;
import com.example.system.model.vo.SimpleRoleVo;
import com.example.system.model.vo.RoleVo;
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
    public List<SimpleRoleVo> findAll() {
        return service.findAll();
    }
}
