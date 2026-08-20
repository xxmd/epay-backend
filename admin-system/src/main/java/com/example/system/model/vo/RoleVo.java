package com.example.system.model.vo;

import com.example.crud.model.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class RoleVo extends BaseVo {
    private String label;

    private String value;

    private Set<MenuVo> menuSet;
}
