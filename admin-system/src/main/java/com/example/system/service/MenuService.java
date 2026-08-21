package com.example.system.service;

import com.example.crud.service.EntityCrudService;
import com.example.system.domain.dto.MenuDto;
import com.example.common.domain.entity.Menu;
import com.example.system.domain.query.MenuQueryCondition;
import com.example.system.domain.vo.MenuVo;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.*;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class MenuService extends EntityCrudService<Menu, MenuQueryCondition, MenuVo, MenuDto> {
    public List<MenuVo> findAll() {
        List<MenuVo> menuVoList = repository.findAll().stream()
                .map(mapper::toVo)
                .toList();

        Map<Long, List<MenuVo>> childrenGroup = menuVoList.stream()
                .filter(vo -> vo.getParentId() != null)
                .collect(Collectors.groupingBy(MenuVo::getParentId));

        Comparator<MenuVo> bySort = Comparator.comparing(MenuVo::getSort, Comparator.nullsLast(Comparator.naturalOrder()));

        for (MenuVo vo : menuVoList) {
            List<MenuVo> children = childrenGroup.get(vo.getId());
            vo.setHasChildren(children != null && !children.isEmpty());
            if (children != null) {
                vo.setChildren(children.stream().sorted(bySort).collect(Collectors.toList()));
            }
        }

        return menuVoList.stream()
                .filter(vo -> vo.getParentId() == null)
                .sorted(bySort)
                .collect(Collectors.toList());
    }
}
