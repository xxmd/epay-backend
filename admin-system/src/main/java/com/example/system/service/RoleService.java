package com.example.system.service;

import com.example.crud.service.EntityCrudService;
import com.example.system.mapper.RoleMapper;
import com.example.system.domain.dto.RoleDto;
import com.example.common.domain.entity.Role;
import com.example.system.domain.query.RoleQueryCondition;
import com.example.system.domain.vo.SimpleRoleVo;
import com.example.system.domain.vo.RoleVo;
import com.example.common.repository.MenuRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.HashSet;
import java.util.List;

@Service
@AllArgsConstructor
public class RoleService extends EntityCrudService<Role, RoleQueryCondition, RoleVo, RoleDto> {
    private final RoleMapper roleMapper;
    private final MenuRepository menuRepository;

    public List<SimpleRoleVo> findAll() {
        return repository.findAll().stream()
                .map(roleMapper::toSimpleVo)
                .toList();
    }

    @Override
    public Role dtoToEntity(RoleDto dto) {
        Role entity = super.dtoToEntity(dto);
        entity.setMenuSet(new HashSet<>(menuRepository.findAllById(dto.getMenuIdSet())));
        return entity;
    }
}
