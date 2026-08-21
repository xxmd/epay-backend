package com.example.pay.service;

import com.example.crud.domain.annotation.RequireCreatedBy;
import com.example.crud.service.EntityCrudService;
import com.example.file.repository.LocalFileRepository;
import com.example.pay.domain.dto.ApplicationDto;
import com.example.pay.domain.entity.Application;
import com.example.pay.domain.query.ApplicationQueryCondition;
import com.example.pay.domain.vo.ApplicationVo;
import com.example.pay.domain.vo.SimpleApplicationVo;
import com.example.pay.mapper.ApplicationMapper;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.List;

@RequireCreatedBy
@Service
@AllArgsConstructor
public class ApplicationService extends EntityCrudService<Application, ApplicationQueryCondition, ApplicationVo, ApplicationDto> {
    private final ApplicationMapper mapper;
    private final LocalFileRepository localFileRepository;

    @Override
    public Application dtoToEntity(ApplicationDto dto) {
        Application entity = super.dtoToEntity(dto);
        entity.setIconFile(localFileRepository.getReferenceById(dto.getIconFileId()));
        return entity;
    }

    public List<SimpleApplicationVo> findAll() {
        List<Application> list = repository.findAll();
        return mapper.toSimpleVoList(list);
    }
}
