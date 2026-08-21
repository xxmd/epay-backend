package com.example.system.domain.vo;

import com.example.crud.domain.vo.BaseVo;
import com.example.common.domain.enums.MenuType;
import lombok.Getter;
import lombok.Setter;

import java.util.List;

@Getter
@Setter
public class MenuVo extends BaseVo {
    private Long parentId;

    private MenuType type;

    private String title;

    private String path;

    private String component;

    private String permission;

    private Integer sort;

    private Boolean hidden;

    private Boolean hasChildren;

    private List<MenuVo> children;
}
