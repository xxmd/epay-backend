package com.example.pay.controller;

import com.example.crud.controller.EntityCrudController;
import com.example.pay.domain.dto.MethodDto;
import com.example.pay.domain.entity.Method;
import com.example.pay.domain.query.MethodQueryCondition;
import com.example.pay.domain.vo.MethodVo;
import com.example.crud.model.annotation.PermissionPrefix;
import com.example.pay.domain.vo.SimpleMethodVo;
import com.example.pay.service.MethodService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pay/method")
@PermissionPrefix("pay:method")
@AllArgsConstructor
public class MethodController extends EntityCrudController<Method, MethodQueryCondition, MethodVo, MethodDto> {
    private final MethodService service;

    @GetMapping("/findAll")
    public List<SimpleMethodVo> findAll() {
        return service.findAll();
    }
}
