package com.example.system.model.entity;

import com.example.common.model.entity.BaseEntity;
import jakarta.persistence.*;
import jakarta.persistence.ManyToMany;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
@Entity
@Table(name = "sys_user")
public class User extends BaseEntity {
    private String username;

    private String password;

    private String nickname;

    private String email;

    private Boolean enabled;

    @ManyToMany
    @JoinTable(name = "sys_user_role", joinColumns = {@JoinColumn(name = "user_id")}, inverseJoinColumns = {@JoinColumn(name = "role_id")})
    private Set<Role> roleSet;
}
