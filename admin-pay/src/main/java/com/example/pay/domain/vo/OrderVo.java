package com.example.pay.domain.vo;

import com.example.crud.model.vo.BaseVo;
import com.example.pay.domain.enums.PayStatus;
import lombok.Data;

import java.math.BigDecimal;
import java.util.Date;

@Data
public class OrderVo extends BaseVo {

    private SimpleMethodVo method;

    private String orderNumber;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private BigDecimal totalAmount;

    private String payUrl;

    private PayStatus payStatus;

    private Date payDate;

    private String notifyParam;

    private String remark;

    private SimpleMerchantVo merchant;

    private SimpleApplicationVo application;
}
