package com.example.pay.service;

import com.example.crud.model.annotation.DataPermission;
import com.example.crud.service.EntityCrudService;
import com.example.file.repository.LocalFileRepository;
import com.example.pay.domain.dto.ApplicationDto;
import com.example.pay.domain.entity.Application;
import com.example.pay.domain.query.ApplicationQueryCondition;
import com.example.pay.domain.vo.ApplicationVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@DataPermission
@Service
@AllArgsConstructor
public class ApplicationService extends EntityCrudService<Application, ApplicationQueryCondition, ApplicationVo, ApplicationDto> {

    private final LocalFileRepository localFileRepository;

    @Override
    public Application dtoToEntity(ApplicationDto dto) {
        Application entity = super.dtoToEntity(dto);
        entity.setIconFile(localFileRepository.getReferenceById(dto.getIconFileId()));
        return entity;
    }
}
