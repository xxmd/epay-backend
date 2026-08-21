package com.example.pay.domain.entity;

import com.example.common.model.entity.BaseEntity;
import com.example.pay.domain.enums.PayStatus;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.math.BigDecimal;
import java.util.Date;

@Getter
@Setter
@Entity
@Table(name = "pay_order")
public class Order extends BaseEntity {
    private String orderNumber;

    private String productName;

    private BigDecimal productPrice;

    private Integer productQuantity;

    private String payUrl;

    @Enumerated(EnumType.STRING)
    private PayStatus payStatus = PayStatus.UNPAID;

    private Date payDate;

    private String notifyParam;

    private String remark;

    @ManyToOne
    private Merchant merchant;

    @ManyToOne
    private Application application;

    @ManyToOne
    private Method method;

    public BigDecimal getTotalAmount() {
        if (productPrice == null || productQuantity == null) {
            return BigDecimal.ZERO;
        }
        return productPrice.multiply(BigDecimal.valueOf(productQuantity));
    }
}
