package com.example.pay.service;

import com.example.crud.service.EntityCrudService;
import com.example.pay.domain.dto.PlatformDto;
import com.example.pay.domain.entity.Platform;
import com.example.pay.domain.query.PlatformQueryCondition;
import com.example.pay.domain.vo.PlatformVo;
import com.example.pay.domain.vo.SimplePlatformVo;
import com.example.pay.mapper.PlatformMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class PlatformService extends EntityCrudService<Platform, PlatformQueryCondition, PlatformVo, PlatformDto> {
    private final PlatformMapper mapper;

    public List<SimplePlatformVo> findAll() {
        return mapper.toSimpleVoList(repository.findAll());
    }
}
