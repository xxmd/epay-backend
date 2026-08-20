package com.example.pay.controller;

import com.example.crud.controller.EntityCrudController;
import com.example.crud.model.annotation.PermissionPrefix;
import com.example.pay.domain.dto.ApplicationDto;
import com.example.pay.domain.entity.Application;
import com.example.pay.domain.query.ApplicationQueryCondition;
import com.example.pay.domain.vo.ApplicationVo;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/application")
@PermissionPrefix("pay:application")
@AllArgsConstructor
public class ApplicationController extends EntityCrudController<Application, ApplicationQueryCondition, ApplicationVo, ApplicationDto> {
}
