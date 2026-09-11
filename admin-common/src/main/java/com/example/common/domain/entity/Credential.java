package com.example.common.domain.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pay_credential")
public class Credential extends BaseEntity {
    private String name;

    private String accessKey;

    private String accessSecret;

    private Boolean enabled;

    private String remark;

    @ManyToOne
    private User user;
}
