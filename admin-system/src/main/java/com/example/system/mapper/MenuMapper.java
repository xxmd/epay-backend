package com.example.system.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.system.domain.dto.MenuDto;
import com.example.common.domain.entity.Menu;
import com.example.system.domain.vo.MenuVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends BaseMapper<Menu, MenuVo, MenuDto> {
}
