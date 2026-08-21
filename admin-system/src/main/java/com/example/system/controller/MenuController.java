package com.example.system.controller;

import com.example.common.domain.Result;
import com.example.crud.controller.EntityCrudController;
import com.example.crud.domain.annotation.PermissionPrefix;
import com.example.system.domain.dto.MenuDto;
import com.example.common.domain.entity.Menu;
import com.example.system.domain.query.MenuQueryCondition;
import com.example.system.domain.vo.MenuVo;
import com.example.system.service.MenuService;
import lombok.AllArgsConstructor;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/system/menu")
@PermissionPrefix("system:menu")
@AllArgsConstructor
public class MenuController extends EntityCrudController<Menu, MenuQueryCondition, MenuVo, MenuDto> {
    private final MenuService service;

    @GetMapping("/findAll")
    @PreAuthorize("hasAuthority('system:menu:read')")
    public Result<List<MenuVo>> findAll() {
        return Result.success(service.findAll());
    }
}
