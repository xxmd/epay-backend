package com.example.system.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.system.domain.dto.UserDto;
import com.example.common.domain.entity.User;
import com.example.system.domain.vo.UserVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends BaseMapper<User, UserVo, UserDto> {
}
