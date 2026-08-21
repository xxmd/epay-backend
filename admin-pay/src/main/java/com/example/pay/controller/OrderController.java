package com.example.pay.controller;

import com.example.common.domain.Result;
import com.example.common.domain.annotation.Anonymous;
import com.example.crud.controller.EntityCrudController;
import com.example.crud.domain.annotation.PermissionPrefix;
import com.example.crud.domain.annotation.UpdatePermission;
import com.example.pay.domain.dto.OrderDto;
import com.example.pay.domain.entity.EPayNotifyParam;
import com.example.pay.domain.entity.Order;
import com.example.pay.domain.query.OrderQueryCondition;
import com.example.pay.domain.vo.OrderVo;
import com.example.pay.service.OrderService;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;
import tools.jackson.databind.ObjectMapper;

import java.util.Map;

@Slf4j
@RestController
@RequestMapping("/pay/order")
@PermissionPrefix("pay:order")
@AllArgsConstructor
public class OrderController extends EntityCrudController<Order, OrderQueryCondition, OrderVo, OrderDto> {
    private final OrderService orderService;
    private final ObjectMapper objectMapper;

    @Override
    @PostMapping("/update")
    @UpdatePermission
    public Result<Void> update(@RequestBody OrderDto dto) {
        orderService.update(dto);
        return Result.success();
    }

    @Anonymous
    @GetMapping("/notify")
    public String notify(@RequestParam Map<String, String> params) {
        try {
            log.info("notify params: {}", params);
            EPayNotifyParam notifyParam = objectMapper.convertValue(params, EPayNotifyParam.class);
            boolean valid = orderService.isNotifyParamValid(notifyParam);
            if (valid) {
                return "success";
            }
        } catch (Exception e) {
            log.error("处理订单通知异常", e);
        }
        return "failure";
    }
}
