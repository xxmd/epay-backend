package com.example.common.domain.entity;

import com.example.common.domain.enums.MenuType;
import jakarta.persistence.Entity;
import jakarta.persistence.EnumType;
import jakarta.persistence.Enumerated;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;


@Getter
@Setter
@Entity
@Table(name = "sys_menu")
public class Menu extends BaseEntity {
    private Long parentId;

    @Enumerated(EnumType.STRING)
    private MenuType type;

    private String title;

    private String path;

    private String component;

    private String permission;

    private Integer sort;

    private Boolean hidden;
}
