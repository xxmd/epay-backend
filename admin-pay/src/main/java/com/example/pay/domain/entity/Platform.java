package com.example.pay.domain.entity;

import com.example.common.domain.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.OneToMany;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
@Entity
@Table(name = "pay_platform")
public class Platform extends BaseEntity {
    private String name;

    private String domainName;

    private String contact;

    private Integer sort;

    private Boolean enabled;

    private String remark;

    @OneToMany(mappedBy = "platform")
    private List<Merchant> merchantList;
}
