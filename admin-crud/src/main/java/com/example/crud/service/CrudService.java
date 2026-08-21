package com.example.crud.service;

import com.example.common.exception.BusinessException;
import com.example.common.domain.entity.BaseEntity;
import com.example.common.domain.enums.CommonError;
import com.example.crud.domain.enums.CrudError;
import com.example.crud.factory.CrudRepositoryFactory;
import com.example.crud.mapper.BaseMapper;
import com.example.crud.domain.annotation.DataPermission;
import com.example.crud.query.ConditionConverter;
import jakarta.annotation.PostConstruct;
import jakarta.persistence.criteria.Predicate;
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

import java.util.List;
import java.util.Map;
import java.util.Set;

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
        Specification<T> spec = (root, query, criteriaBuilder) -> {
            Predicate predicate = ConditionConverter.toPredicate(root, criteria, criteriaBuilder);
            if (hasDataPermission()) {
                String currentUser = getCurrentUsername();
                Predicate createdByPredicate = criteriaBuilder.equal(root.get("createdBy"), currentUser);
                predicate = predicate == null ? createdByPredicate : criteriaBuilder.and(predicate, createdByPredicate);
            }
            return predicate;
        };
        Page<T> page = repository.findAll(spec, pageable);
        Page<VO> voPage = page.map(mapper::toVo);
        return new PagedModel<>(voPage);
    }

    public void create(DTO dto) {
        T entity = dtoToEntityOnCreate(dto);
        saveOnCreate(entity);
    }

    public void update(DTO dto) {
        if (hasDataPermission()) {
            verifyOwnership(dto);
        }
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
        if (hasDataPermission()) {
            List<T> entities = repository.findAllById(idSet);
            String currentUser = getCurrentUsername();
            for (T entity : entities) {
                String createdBy = getCreatedBy(entity);
                if (!currentUser.equals(createdBy)) {
                    throw new BusinessException(CommonError.FORBIDDEN);
                }
            }
        }
        repository.deleteAllById(idSet);
    }

    protected boolean hasDataPermission() {
        return getClass().isAnnotationPresent(DataPermission.class);
    }

    protected String getCurrentUsername() {
        Authentication authentication = SecurityContextHolder.getContext().getAuthentication();
        if (authentication == null) {
            throw new BusinessException(CrudError.AUTHENTICATION_IS_NULL);
        }
        if (authentication instanceof UsernamePasswordAuthenticationToken authenticationToken) {
            return (String) authenticationToken.getPrincipal();
        }
        throw new BusinessException(CrudError.GET_CURRENT_USER_FAILURE);
    }

    private String getCreatedBy(T entity) {
        if (entity instanceof BaseEntity baseEntity) {
            return baseEntity.getCreatedBy();
        }
        return null;
    }

    @SuppressWarnings("unchecked")
    private void verifyOwnership(DTO dto) {
        try {
            java.lang.reflect.Field idField = dto.getClass().getSuperclass().getDeclaredField("id");
            idField.setAccessible(true);
            Object id = idField.get(dto);
            if (id != null) {
                T entity = repository.findById((ID) id).orElse(null);
                if (entity != null) {
                    String createdBy = getCreatedBy(entity);
                    if (createdBy == null || !getCurrentUsername().equals(createdBy)) {
                        throw new BusinessException(CommonError.FORBIDDEN);
                    }
                }
            }
        } catch (BusinessException e) {
            throw e;
        } catch (Exception e) {
            log.warn("Failed to verify ownership", e);
        }
    }
}