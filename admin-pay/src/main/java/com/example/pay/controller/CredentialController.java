package com.example.pay.controller;

import com.example.pay.domain.dto.CredentialDto;
import com.example.common.domain.entity.Credential;
import com.example.pay.domain.query.CredentialQueryCondition;
import com.example.pay.domain.vo.CredentialVo;
import com.example.pay.domain.vo.SimpleCredentialVo;
import com.example.pay.service.CredentialService;
import com.example.common.domain.Result;
import com.example.crud.controller.EntityCrudController;
import com.example.crud.domain.annotation.PermissionPrefix;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.util.List;

@RestController
@RequestMapping("/pay/credential")
@PermissionPrefix("pay:credential")
@AllArgsConstructor
public class CredentialController extends EntityCrudController<Credential, CredentialQueryCondition, CredentialVo, CredentialDto> {
    private final CredentialService service;

    @GetMapping("/findAll")
    public Result<List<SimpleCredentialVo>> findAll() {
        return Result.success(service.findAll());
    }
}
