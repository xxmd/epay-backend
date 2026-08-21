package com.example.crud.controller;

import com.example.crud.factory.CrudServiceFactory;
import com.example.common.domain.Result;
import com.example.crud.domain.annotation.CreatePermission;
import com.example.crud.domain.annotation.DeletePermission;
import com.example.crud.domain.annotation.ReadPermission;
import com.example.crud.domain.annotation.UpdatePermission;
import com.example.crud.service.CrudService;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ConfigurableApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Pageable;
import org.springframework.data.web.PagedModel;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

import java.util.Set;

public abstract class CrudController<T, ID, QC, VO, DTO, CS extends CrudService<T, ID, QC, VO, DTO>> {
    @Autowired
    protected ConfigurableApplicationContext context;
    @Autowired
    private CrudServiceFactory crudServiceFactory;
    private CS service;

    @PostConstruct
    @SuppressWarnings("unchecked")
    private void initService() {
        if (service != null) {
            return;
        }
        ResolvableType controllerType = ResolvableType.forClass(getClass()).as(CrudController.class);
        ResolvableType entityGeneric = controllerType.getGeneric(0);
        ResolvableType idGeneric = controllerType.getGeneric(1);
        ResolvableType queryConditionGeneric = controllerType.getGeneric(2);
        ResolvableType serviceGeneric = controllerType.getGeneric(5);
        this.service = (CS) crudServiceFactory.findOrCreate(entityGeneric, idGeneric, queryConditionGeneric, serviceGeneric);
    }

    @PostMapping("/create")
    @CreatePermission
    public Result<Void> create(@Validated @RequestBody DTO entity) {
        service.create(entity);
        return Result.success();
    }

    @PostMapping("/read")
    @ReadPermission
    public Result<PagedModel<VO>> read(@RequestBody QC queryCondition, Pageable pageable) {
        return Result.success(service.findAll(queryCondition, pageable));
    }

    @PostMapping("/update")
    @UpdatePermission
    public Result<Void> update(@Validated @RequestBody DTO entity) {
        service.update(entity);
        return Result.success();
    }

    @PostMapping("/delete")
    @DeletePermission
    public Result<Void> delete(@RequestBody Set<ID> idSet) {
        service.delete(idSet);
        return Result.success();
    }
}