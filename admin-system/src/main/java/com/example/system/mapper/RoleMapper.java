package com.example.system.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.system.domain.dto.RoleDto;
import com.example.common.domain.entity.Role;
import com.example.system.domain.vo.SimpleRoleVo;
import com.example.system.domain.vo.RoleVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MenuMapper.class})
public interface RoleMapper extends BaseMapper<Role, RoleVo, RoleDto> {
    SimpleRoleVo toSimpleVo(Role role);
}
