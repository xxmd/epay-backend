package com.example.file.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.file.domain.dto.LocalFileDto;
import com.example.file.domain.entity.LocalFile;
import com.example.file.domain.vo.LocalFileVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring")
public interface LocalFileMapper extends BaseMapper<LocalFile, LocalFileVo, LocalFileDto> {
}
