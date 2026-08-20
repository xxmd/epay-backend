package com.example.pay.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.file.mapper.LocalFileMapper;
import com.example.pay.domain.dto.ApplicationDto;
import com.example.pay.domain.entity.Application;
import com.example.pay.domain.vo.ApplicationVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {LocalFileMapper.class})
public interface ApplicationMapper extends BaseMapper<Application, ApplicationVo, ApplicationDto> {
}
