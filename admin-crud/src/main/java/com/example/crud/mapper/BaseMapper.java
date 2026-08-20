package com.example.crud.mapper;

import java.util.List;

public interface BaseMapper<E, VO, DTO> {
    VO toVo(E entity);

    List<VO> toVo(List<E> entity);

    E toEntity(DTO dto);
}
