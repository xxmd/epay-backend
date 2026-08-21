package com.example.pay.controller;

import com.example.crud.controller.EntityCrudController;
import com.example.pay.domain.dto.MerchantDto;
import com.example.pay.domain.entity.Merchant;
import com.example.pay.domain.query.MerchantQueryCondition;
import com.example.pay.domain.vo.MerchantVo;
import com.example.crud.domain.annotation.PermissionPrefix;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/pay/merchant")
@PermissionPrefix("pay:merchant")
@AllArgsConstructor
public class MerchantController extends EntityCrudController<Merchant, MerchantQueryCondition, MerchantVo, MerchantDto> {
}
