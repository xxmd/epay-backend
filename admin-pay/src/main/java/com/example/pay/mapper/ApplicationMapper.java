package com.example.pay.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.file.mapper.LocalFileMapper;
import com.example.pay.domain.dto.ApplicationDto;
import com.example.pay.domain.entity.Application;
import com.example.pay.domain.entity.Method;
import com.example.pay.domain.vo.ApplicationVo;
import com.example.pay.domain.vo.SimpleApplicationVo;
import com.example.pay.domain.vo.SimpleMethodVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {LocalFileMapper.class})
public interface ApplicationMapper extends BaseMapper<Application, ApplicationVo, ApplicationDto> {
    SimpleApplicationVo toSimpleVo(Application entity);

    List<SimpleApplicationVo> toSimpleVoList(List<Application> entityList);
}
