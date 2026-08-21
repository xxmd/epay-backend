package com.example.crud.service;

import com.example.crud.factory.CrudRepositoryFactory;
import com.example.crud.mapper.BaseMapper;
import com.example.crud.query.ConditionConverter;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;
import jakarta.persistence.criteria.Root;
import lombok.extern.slf4j.Slf4j;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.ResolvableType;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.data.web.PagedModel;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.context.WebApplicationContext;

import java.util.Map;
import java.util.Set;

/**
 * CRUD 基础服务。
 * <p>
 * 数据权限由 {@link com.example.crud.domain.annotation.RequireCreatedBy} 注解控制，
 * 标注在类或方法上后，底层 {@link com.example.crud.repository.DataPermissionJpaRepository}
 * 自动拦截 JPA 操作，过滤/校验 createdBy。
 */
@Slf4j
@Transactional
public abstract class CrudService<T, ID, QC, VO, DTO> {
    @Autowired
    private WebApplicationContext context;
    @Autowired
    private CrudRepositoryFactory repositoryFactory;
    protected BaseMapper<T, VO, DTO> mapper;
    protected SimpleJpaRepository<T, ID> repository;

    @PostConstruct
    public void postConstruct() {
        initRepository();
        initMapper();
    }

    @SuppressWarnings("unchecked")
    private void initRepository() {
        if (repository != null) {
            return;
        }
        ResolvableType resolvableType = ResolvableType.forClass(getClass()).as(CrudService.class);
        ResolvableType entityGeneric = resolvableType.getGeneric(0);
        ResolvableType idGeneric = resolvableType.getGeneric(1);
        this.repository = repositoryFactory.findOrCreate(entityGeneric, idGeneric);
    }

    @SuppressWarnings("unchecked")
    private void initMapper() {
        if (mapper != null) {
            return;
        }
        ResolvableType resolvableType = ResolvableType.forClass(getClass()).as(CrudService.class);
        ResolvableType entityGeneric = resolvableType.getGeneric(0);
        ResolvableType voGeneric = resolvableType.getGeneric(3);
        ResolvableType dtoGeneric = resolvableType.getGeneric(4);
        Map<String, BaseMapper> mapperBeans = context.getBeansOfType(BaseMapper.class);
        for (BaseMapper<?, ?, ?> mapperBean : mapperBeans.values()) {
            ResolvableType mapperType = ResolvableType.forClass(AopUtils.getTargetClass(mapperBean)).as(BaseMapper.class);
            ResolvableType actualEntityGeneric = mapperType.getGeneric(0);
            ResolvableType actualVoGeneric = mapperType.getGeneric(1);
            ResolvableType actualDtoGeneric = mapperType.getGeneric(2);
            if (entityGeneric.isAssignableFrom(actualEntityGeneric)
                    && voGeneric.isAssignableFrom(actualVoGeneric)
                    && dtoGeneric.isAssignableFrom(actualDtoGeneric)) {
                this.mapper = (BaseMapper<T, VO, DTO>) mapperBean;
                return;
            }
        }
        throw new IllegalStateException("No matching BaseMapper found for " + getClass().getSimpleName());
    }

    public PagedModel<VO> findAll(QC criteria, Pageable pageable) {
        Specification<T> spec = (root, query, cb) -> ConditionConverter.toPredicate(root, criteria, cb);
        Page<T> page = repository.findAll(spec, pageable);
        return new PagedModel<>(page.map(mapper::toVo));
    }

    public void create(DTO dto) {
        T entity = dtoToEntityOnCreate(dto);
        saveOnCreate(entity);
    }

    public void update(DTO dto) {
        T entity = dtoToEntityOnUpdate(dto);
        saveOnUpdate(entity);
    }

    public T dtoToEntity(DTO dto) {
        return mapper.toEntity(dto);
    }

    public T dtoToEntityOnCreate(DTO dto) {
        return dtoToEntity(dto);
    }

    public T dtoToEntityOnUpdate(DTO dto) {
        return dtoToEntity(dto);
    }

    public void save(T entity) {
        repository.save(entity);
    }

    public void saveOnCreate(T entity) {
        save(entity);
    }

    public void saveOnUpdate(T entity) {
        save(entity);
    }

    public void delete(Set<ID> idSet) {
        repository.deleteAllById(idSet);
    }

    protected String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication instanceof UsernamePasswordAuthenticationToken token) {
            return (String) token.getPrincipal();
        }
        throw new RuntimeException("Failed to get current username");
    }

    protected Predicate buildCreatedByPredicate(Root<?> root, CriteriaBuilder cb) {
        return cb.equal(root.get("createdBy"), getCurrentUsername());
    }
}
