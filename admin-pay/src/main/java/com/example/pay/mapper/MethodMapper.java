package com.example.pay.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.pay.domain.dto.MethodDto;
import com.example.pay.domain.entity.Method;
import com.example.pay.domain.entity.Platform;
import com.example.pay.domain.vo.MethodVo;
import com.example.pay.domain.vo.SimpleMethodVo;
import com.example.pay.domain.vo.SimplePlatformVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface MethodMapper extends BaseMapper<Method, MethodVo, MethodDto> {
    SimpleMethodVo toSimpleVo(Method method);

    List<SimpleMethodVo> toSimpleVoList(List<Method> methodList);
}
