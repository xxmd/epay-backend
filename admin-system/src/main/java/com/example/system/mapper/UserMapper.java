package com.example.system.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.system.model.dto.UserDto;
import com.example.system.model.entity.User;
import com.example.system.model.vo.UserVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {RoleMapper.class})
public interface UserMapper extends BaseMapper<User, UserVo, UserDto> {
}
