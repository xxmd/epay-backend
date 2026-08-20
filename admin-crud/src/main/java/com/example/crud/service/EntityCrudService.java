package com.example.crud.service;

import com.example.crud.model.dto.BaseDto;
import com.example.common.model.entity.BaseEntity;
import com.example.crud.model.vo.BaseVo;

public abstract class EntityCrudService<T extends BaseEntity, QC, VO extends BaseVo, DTO extends BaseDto> extends CrudService<T, Long, QC, VO, DTO> {
    @Override
    public void create(DTO dto) {
        if (dto.getId() != null) {
            throw new IllegalArgumentException("Id must be null when create");
        }
        super.create(dto);
    }

    @Override
    public void update(DTO dto) {
        if (dto.getId() == null) {
            throw new IllegalArgumentException("Id can't be null when update");
        }
        super.update(dto);
    }
}

