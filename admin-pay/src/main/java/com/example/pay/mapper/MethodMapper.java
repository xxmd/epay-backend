package com.example.pay.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.pay.domain.dto.MethodDto;
import com.example.pay.domain.entity.Method;
import com.example.pay.domain.vo.MethodVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface MethodMapper extends BaseMapper<Method, MethodVo, MethodDto> {
}
