package com.example.pay.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.pay.domain.dto.OrderDto;
import com.example.pay.domain.entity.Order;
import com.example.pay.domain.vo.OrderVo;
import com.example.pay.domain.vo.SimpleApplicationVo;
import com.example.pay.domain.vo.SimpleMerchantVo;
import com.example.pay.domain.vo.SimpleMethodVo;
import com.example.pay.domain.entity.Application;
import com.example.pay.domain.entity.Merchant;
import com.example.pay.domain.entity.Method;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MerchantMapper.class, MethodMapper.class, ApplicationMapper.class})
public interface OrderMapper extends BaseMapper<Order, OrderVo, OrderDto> {
}
