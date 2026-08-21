package com.example.pay.controller;

import com.example.common.domain.Result;
import com.example.crud.controller.EntityCrudController;
import com.example.crud.domain.annotation.PermissionPrefix;
import com.example.pay.domain.dto.ApplicationDto;
import com.example.pay.domain.entity.Application;
import com.example.pay.domain.query.ApplicationQueryCondition;
import com.example.pay.domain.vo.ApplicationVo;
import com.example.pay.domain.vo.SimpleApplicationVo;
import com.example.pay.service.ApplicationService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pay/application")
@PermissionPrefix("pay:application")
@AllArgsConstructor
public class ApplicationController extends EntityCrudController<Application, ApplicationQueryCondition, ApplicationVo, ApplicationDto> {
    private final ApplicationService service;

    @GetMapping("/findAll")
    public Result<List<SimpleApplicationVo>> findAll() {
        return Result.success(service.findAll());
    }
}
