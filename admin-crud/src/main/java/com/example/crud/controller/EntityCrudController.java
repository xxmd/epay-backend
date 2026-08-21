package com.example.crud.controller;

import com.example.crud.domain.dto.BaseDto;
import com.example.common.domain.entity.BaseEntity;
import com.example.crud.domain.vo.BaseVo;
import com.example.crud.service.EntityCrudService;

public abstract class EntityCrudController<T extends BaseEntity, QC, VO extends BaseVo, DTO extends BaseDto> extends CrudController<T, Long, QC, VO, DTO, EntityCrudService<T, QC, VO, DTO>> {
}
