package com.example.pay.service;

import com.example.crud.service.EntityCrudService;
import com.example.pay.domain.dto.MethodDto;
import com.example.pay.domain.dto.PlatformDto;
import com.example.pay.domain.entity.Method;
import com.example.pay.domain.entity.Platform;
import com.example.pay.domain.query.MethodQueryCondition;
import com.example.pay.domain.query.PlatformQueryCondition;
import com.example.pay.domain.vo.MethodVo;
import com.example.pay.domain.vo.PlatformVo;
import com.example.pay.domain.vo.SimpleMethodVo;
import com.example.pay.domain.vo.SimplePlatformVo;
import com.example.pay.mapper.MethodMapper;
import com.example.pay.mapper.PlatformMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@Service
@AllArgsConstructor
public class MethodService extends EntityCrudService<Method, MethodQueryCondition, MethodVo, MethodDto> {
    private final MethodMapper mapper;

    public List<SimpleMethodVo> findAll() {
        return mapper.toSimpleVoList(repository.findAll());
    }
}
