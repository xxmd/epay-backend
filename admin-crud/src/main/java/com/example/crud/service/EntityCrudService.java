package com.example.crud.service;

import com.example.common.exception.BusinessException;
import com.example.crud.domain.dto.BaseDto;
import com.example.common.domain.entity.BaseEntity;
import com.example.crud.domain.enums.CrudError;
import com.example.crud.domain.vo.BaseVo;

public abstract class EntityCrudService<T extends BaseEntity, QC, VO extends BaseVo, DTO extends BaseDto> extends CrudService<T, Long, QC, VO, DTO> {
    @Override
    public void create(DTO dto) {
        if (dto.getId() != null) {
            throw new BusinessException(CrudError.ID_NOT_NULL_WHEN_CREATE);
        }
        super.create(dto);
    }

    @Override
    public void update(DTO dto) {
        if (dto.getId() == null) {
            throw new BusinessException(CrudError.ID_IS_NULL_WHEN_UPDATE);
        }
        super.update(dto);
    }
}

