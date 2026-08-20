package com.example.system.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.system.model.dto.MenuDto;
import com.example.system.model.entity.Menu;
import com.example.system.model.vo.MenuVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface MenuMapper extends BaseMapper<Menu, MenuVo, MenuDto> {
}
