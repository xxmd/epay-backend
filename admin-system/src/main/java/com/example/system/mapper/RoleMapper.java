package com.example.system.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.system.model.dto.RoleDto;
import com.example.system.model.entity.Role;
import com.example.system.model.vo.SimpleRoleVo;
import com.example.system.model.vo.RoleVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {MenuMapper.class})
public interface RoleMapper extends BaseMapper<Role, RoleVo, RoleDto> {
    SimpleRoleVo toSimpleVo(Role role);
}
