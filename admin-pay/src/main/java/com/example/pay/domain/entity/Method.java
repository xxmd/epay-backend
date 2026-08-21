package com.example.pay.domain.entity;

import com.example.common.domain.entity.BaseEntity;
import jakarta.persistence.Entity;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pay_method")
public class Method extends BaseEntity {
    private String label;

    private String value;

    private Boolean enabled;
}
