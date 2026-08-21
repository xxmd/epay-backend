package com.example.pay.domain.vo;

import com.example.pay.domain.entity.Method;
import com.example.crud.domain.vo.BaseVo;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.JoinTable;
import jakarta.persistence.ManyToMany;
import lombok.Data;

import java.util.List;

@Data
public class MerchantVo extends BaseVo {
    private Integer merchantId;

    private String md5SecretKey;

    private Integer sort;

    private Boolean enabled;

    private String remark;

    private SimplePlatformVo platform;

    @ManyToMany
    @JoinTable(name = "pay_merchant_method", joinColumns = @JoinColumn(name = "merchant_id"), inverseJoinColumns = @JoinColumn(name = "method_id"))
    private List<Method> methodList;
}
