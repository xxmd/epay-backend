package com.example.crud.controller;

import com.example.crud.model.dto.BaseDto;
import com.example.common.model.entity.BaseEntity;
import com.example.crud.model.vo.BaseVo;
import com.example.crud.service.EntityCrudService;

public abstract class EntityCrudController<T extends BaseEntity, QC, VO extends BaseVo, DTO extends BaseDto> extends CrudController<T, Long, QC, VO, DTO, EntityCrudService<T, QC, VO, DTO>> {
}
