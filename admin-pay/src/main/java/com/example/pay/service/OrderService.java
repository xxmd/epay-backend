package com.example.pay.service;

import com.example.common.exception.BusinessException;
import com.example.common.domain.enums.CommonError;
import com.example.crud.domain.annotation.IgnoreDataPermission;
import com.example.crud.domain.annotation.RequireCreatedBy;
import com.example.crud.service.EntityCrudService;
import com.example.pay.domain.dto.OrderDto;
import com.example.pay.domain.entity.EPayNotifyParam;
import com.example.pay.domain.entity.Merchant;
import com.example.pay.domain.entity.Order;
import com.example.pay.domain.enums.PayError;
import com.example.pay.domain.enums.PayStatus;
import com.example.pay.domain.query.OrderQueryCondition;
import com.example.pay.domain.vo.OrderVo;
import com.example.pay.repository.ApplicationRepository;
import com.example.pay.repository.MerchantRepository;
import com.example.pay.repository.MethodRepository;
import com.example.pay.repository.OrderRepository;
import io.github.xxmd.epay.api.EPayApiV1;
import io.github.xxmd.epay.entity.enums.PayMethod;
import io.github.xxmd.epay.entity.param.RedirectPayParam;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;
import tools.jackson.databind.ObjectMapper;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.*;
import java.util.concurrent.TimeUnit;

@Slf4j
@Service
@RequiredArgsConstructor
@RequireCreatedBy
public class OrderService extends EntityCrudService<Order, OrderQueryCondition, OrderVo, OrderDto> {

    private final MerchantRepository merchantRepository;
    private final ApplicationRepository applicationRepository;
    private final MethodRepository methodRepository;
    private final OrderRepository orderRepository;
    private final StringRedisTemplate redisTemplate;
    private final ObjectMapper objectMapper;

    @Value("${epay.notifyUrl}")
    private String notifyUrl;

    @Override
    public Order dtoToEntity(OrderDto dto) {
        Order entity = super.dtoToEntity(dto);
        entity.setApplication(applicationRepository.getReferenceById(dto.getApplicationId()));
        entity.setMethod(methodRepository.getReferenceById(dto.getMethodId()));
        return entity;
    }

    @Override
    public void create(OrderDto dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Id must be null when create");
        }
        Order entity = dtoToEntity(dto);
        List<Merchant> merchants = merchantRepository.findEnabledMerchantsByMethodId(dto.getMethodId());
        if (merchants.isEmpty()) {
            throw new BusinessException(CommonError.NO_AVAILABLE_MERCHANT);
        }
        entity.setMerchant(merchants.get(0));
        entity.setOrderNumber(generateOrderNumber());
        EPayApiV1 ePayApi = buildEPayApi(entity);
        RedirectPayParam redirectPayParam = buildRedirectPayParam(entity);
        entity.setPayUrl(ePayApi.pageRedirectPay(redirectPayParam));
        repository.save(entity);
    }

    @Override
    public void update(OrderDto dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id can't be null when update");
        }
        Order entity = repository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException(PayError.ORDER_NOT_EXISTED));
        entity.setRemark(dto.getRemark());
        repository.save(entity);
    }

    public String generateOrderNumber() {
        String dateStr = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMddHHmmss"));
        String redisKey = "order:seq:" + dateStr.substring(0, 8);
        Long sequence = redisTemplate.opsForValue().increment(redisKey);
        if (sequence != null && sequence == 1) {
            redisTemplate.expire(redisKey, 24, TimeUnit.HOURS);
        }
        String seqStr = String.format("%06d", sequence);
        return dateStr + seqStr;
    }

    @IgnoreDataPermission
    public boolean isNotifyParamValid(EPayNotifyParam notifyParam) {
        if (!notifyParam.isValid()) {
            log.warn("notifyParam invalid: {}", notifyParam);
            return false;
        }
        String orderNumber = notifyParam.getOutTradeNo();
        Optional<Order> orderOptional = orderRepository.findByOrderNumber(orderNumber);
        if (orderOptional.isEmpty()) {
            return false;
        }
        Order order = orderOptional.get();
        if (order.getPayStatus() == PayStatus.PAID) {
            return true;
        }
        TreeMap<String, Object> treeMap = parseReturnParam(notifyParam);
        EPayApiV1 ePayApi = buildEPayApi(order);
        String signature = ePayApi.signParamMap(treeMap);
        if (signature.equals(notifyParam.getSign())) {
            order.setPayStatus(PayStatus.PAID);
            order.setPayDate(new Date());
            order.setNotifyParam(objectMapper.writeValueAsString(notifyParam));
            repository.save(order);
            return true;
        }
        return false;
    }

    private EPayApiV1 buildEPayApi(Order entity) {
        return buildEPayApi(entity.getMerchant());
    }

    private EPayApiV1 buildEPayApi(Merchant merchant) {
        String pid = String.valueOf(merchant.getMerchantId());
        String md5SecretKey = merchant.getMd5SecretKey();
        String domainName = merchant.getPlatform().getDomainName();
        return new EPayApiV1(pid, md5SecretKey, domainName);
    }

    private RedirectPayParam buildRedirectPayParam(Order entity) {
        RedirectPayParam param = new RedirectPayParam();
        param.setPayMethod(PayMethod.valueOf(entity.getMethod().getValue()));
        param.setOutTradeNo(entity.getOrderNumber());
        param.setNotifyUrl(notifyUrl);
        param.setReturnUrl(notifyUrl);
        String name = String.format(Locale.US, "%sx%d", entity.getProductName(), entity.getProductQuantity());
        param.setName(name);
        param.setMoney(entity.getTotalAmount());
        return param;
    }

    private TreeMap<String, Object> parseReturnParam(EPayNotifyParam returnParam) {
        TreeMap<String, Object> treeMap = new TreeMap<>();
        treeMap.put("pid", returnParam.getPid());
        treeMap.put("trade_no", returnParam.getTradeNo());
        treeMap.put("out_trade_no", returnParam.getOutTradeNo());
        treeMap.put("type", returnParam.getType());
        treeMap.put("name", returnParam.getName());
        treeMap.put("money", returnParam.getMoney());
        treeMap.put("trade_status", returnParam.getTradeStatus());
        return treeMap;
    }
}
