package com.example.pay.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.pay.domain.dto.PlatformDto;
import com.example.pay.domain.entity.Platform;
import com.example.pay.domain.vo.PlatformVo;
import com.example.pay.domain.vo.SimplePlatformVo;
import org.mapstruct.Mapper;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface PlatformMapper extends BaseMapper<Platform, PlatformVo, PlatformDto> {
    SimplePlatformVo toSimpleVo(Platform platform);

    List<SimplePlatformVo> toSimpleVoList(List<Platform> platformList);
}
