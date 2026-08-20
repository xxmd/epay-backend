package com.example.system.model.vo;

import com.example.crud.model.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

import java.util.Set;

@Getter
@Setter
public class UserVo extends BaseVo {
    private String username;

    private String nickname;

    private String email;

    private boolean enabled;

    private Set<RoleVo> roleSet;
}
