package com.example.pay.controller;

import com.example.crud.controller.EntityCrudController;
import com.example.pay.domain.dto.PlatformDto;
import com.example.pay.domain.entity.Platform;
import com.example.pay.domain.query.PlatformQueryCondition;
import com.example.pay.domain.vo.PlatformVo;
import com.example.pay.domain.vo.SimplePlatformVo;
import com.example.crud.model.annotation.PermissionPrefix;
import com.example.pay.service.PlatformService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pay/platform")
@PermissionPrefix("pay:platform")
@AllArgsConstructor
public class PlatformController extends EntityCrudController<Platform, PlatformQueryCondition, PlatformVo, PlatformDto> {
    private final PlatformService service;

    @GetMapping("/findAll")
    public List<SimplePlatformVo> findAll() {
        return service.findAll();
    }
}
