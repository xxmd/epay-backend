package com.example.pay.controller;

import com.example.crud.controller.EntityCrudController;
import com.example.pay.domain.dto.MethodDto;
import com.example.pay.domain.entity.Method;
import com.example.pay.domain.query.MethodQueryCondition;
import com.example.pay.domain.vo.MethodVo;
import com.example.crud.model.annotation.PermissionPrefix;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/method")
@PermissionPrefix("pay:method")
@AllArgsConstructor
public class MethodController extends EntityCrudController<Method, MethodQueryCondition, MethodVo, MethodDto> {
}
