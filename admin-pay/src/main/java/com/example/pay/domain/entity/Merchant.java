package com.example.pay.domain.entity;

import com.example.common.model.entity.BaseEntity;
import com.example.pay.domain.entity.Method;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pay_merchant")
public class Merchant extends BaseEntity {
    private Integer merchantId;

    @Column(name = "md5_secret_key")
    private String md5SecretKey;

    private Integer sort;

    private Boolean enabled;

    private String remark;

    @ManyToOne
    @JoinColumn(name = "platform_id")
    private Platform platform;

    @ManyToMany
    @JoinTable(name = "pay_merchant_method", joinColumns = @JoinColumn(name = "merchant_id"), inverseJoinColumns = @JoinColumn(name = "method_id"))
    private List<Method> methodList;
}
