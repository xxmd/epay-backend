package com.example.pay.domain.entity;

import com.example.common.domain.entity.BaseEntity;
import com.example.file.domain.entity.LocalFile;
import com.example.pay.domain.enums.Platform;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
@Entity
@Table(name = "pay_application")
public class Application extends BaseEntity {
    @OneToOne
    private LocalFile iconFile;

    private String name;

    @Enumerated(EnumType.STRING)
    private Platform platform;

    private Boolean enabled;

    private String remark;
}
