package com.example.common.domain.entity;

import jakarta.persistence.*;
import jakarta.validation.constraints.NotBlank;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sys_role")
public class Role extends BaseEntity {
    @NotBlank(message = "角色标签不能为空")
    private String label;

    @NotBlank(message = "角色值不能为空")
    private String value;

    @ManyToMany
    @JoinTable(name = "sys_role_menu", joinColumns = {@JoinColumn(name = "role_id")}, inverseJoinColumns = {@JoinColumn(name = "menu_id")})
    private Set<Menu> menuSet;
}
