package com.example.pay.domain.entity;

import com.example.common.model.entity.BaseEntity;
import com.example.pay.domain.entity.Merchant;
import com.example.file.domain.entity.LocalFile;
import com.example.pay.domain.enums.PayStatus;
import com.example.pay.domain.enums.Platform;
import com.example.pay.domain.entity.Method;
import com.fasterxml.jackson.annotation.JsonIgnoreProperties;
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

    private Date payTime;

    private String notifyParam;

    private String remark;

    @ManyToOne
    @JoinColumn(name = "merchant_id")
    private Merchant merchant;

    @ManyToOne
    @JoinColumn(name = "method_id")
    private Method method;

    public BigDecimal getTotalAmount() {
        if (productPrice == null || productQuantity == null) {
            return BigDecimal.ZERO;
        }
        return productPrice.multiply(BigDecimal.valueOf(productQuantity));
    }
}
