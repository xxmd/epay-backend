package com.example.crud.repository;

import com.example.auth.util.AuthContext;
import com.example.common.exception.BusinessException;
import com.example.common.domain.entity.BaseEntity;
import com.example.common.domain.enums.CommonError;
import com.example.crud.security.DataPermissionContext;
import jakarta.persistence.EntityManager;
import jakarta.persistence.criteria.Predicate;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.support.JpaEntityInformation;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.Optional;

/**
 * 扩展 SimpleJpaRepository，在 DataPermissionContext 激活时自动拦截 CRUD 操作：
 * <ul>
 *   <li>查询：自动追加 createdBy = 当前用户 过滤条件</li>
 *   <li>写入/删除：校验已有数据的 createdBy 是否属于当前用户</li>
 * </ul>
 */
@Transactional
public class DataPermissionJpaRepository<T, ID> extends SimpleJpaRepository<T, ID> {
    private final AuthContext authContext;

    public DataPermissionJpaRepository(JpaEntityInformation<T, ?> entityInformation,
                                       EntityManager entityManager,
                                       AuthContext authContext) {
        super(entityInformation, entityManager);
        this.authContext = authContext;
    }

    public DataPermissionJpaRepository(Class<T> domainClass,
                                       jakarta.persistence.EntityManager entityManager,
                                       AuthContext authContext) {
        super(domainClass, entityManager);
        this.authContext = authContext;
    }

    // ==================== 查询拦截 ====================

    @Override
    public List<T> findAll() {
        if (DataPermissionContext.isActive()) {
            return findAll(withCreatedBySpec());
        }
        return super.findAll();
    }

    @Override
    public List<T> findAll(Sort sort) {
        if (DataPermissionContext.isActive()) {
            return findAll(withCreatedBySpec(), sort);
        }
        return super.findAll(sort);
    }

    @Override
    public Page<T> findAll(Pageable pageable) {
        if (DataPermissionContext.isActive()) {
            return findAll(withCreatedBySpec(), pageable);
        }
        return super.findAll(pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec) {
        if (DataPermissionContext.isActive()) {
            spec = mergeCreatedBySpec(spec);
        }
        return super.findAll(spec);
    }

    @Override
    public Page<T> findAll(Specification<T> spec, Pageable pageable) {
        if (DataPermissionContext.isActive()) {
            spec = mergeCreatedBySpec(spec);
        }
        return super.findAll(spec, pageable);
    }

    @Override
    public List<T> findAll(Specification<T> spec, Sort sort) {
        if (DataPermissionContext.isActive()) {
            spec = mergeCreatedBySpec(spec);
        }
        return super.findAll(spec, sort);
    }

    @Override
    public Optional<T> findById(ID id) {
        Optional<T> result = super.findById(id);
        if (DataPermissionContext.isActive() && result.isPresent()) {
            verifyEntity(result.get());
        }
        return result;
    }

    @Override
    public List<T> findAllById(Iterable<ID> ids) {
        List<T> entities = super.findAllById(ids);
        if (DataPermissionContext.isActive()) {
            verifyEntities(entities);
        }
        return entities;
    }

    // ==================== 写入拦截 ====================

    @Override
    public <S extends T> S save(S entity) {
        if (DataPermissionContext.isActive() && !isNew(entity)) {
            verifyEntity(entity);
        }
        return super.save(entity);
    }

    // ==================== 删除拦截 ====================

    @Override
    public void deleteById(ID id) {
        if (DataPermissionContext.isActive()) {
            T entity = super.findById(id).orElseThrow(() -> new BusinessException(CommonError.FORBIDDEN));
            verifyEntity(entity);
        }
        super.deleteById(id);
    }

    @Override
    public void deleteAllById(Iterable<? extends ID> ids) {
        if (DataPermissionContext.isActive()) {
            @SuppressWarnings("unchecked")
            Iterable<ID> castIds = (Iterable<ID>) ids;
            List<T> entities = super.findAllById(castIds);
            verifyEntities(entities);
        }
        super.deleteAllById(ids);
    }

    @Override
    public void delete(T entity) {
        if (DataPermissionContext.isActive()) {
            verifyEntity(entity);
        }
        super.delete(entity);
    }

    @Override
    public void deleteAll(Iterable<? extends T> entities) {
        if (DataPermissionContext.isActive()) {
            verifyEntities(entities);
        }
        super.deleteAll(entities);
    }

    @Override
    public void deleteAll() {
        if (DataPermissionContext.isActive()) {
            List<T> entities = super.findAll();
            verifyEntities(entities);
        }
        super.deleteAll();
    }

    // ==================== 内部方法 ====================

    private Specification<T> withCreatedBySpec() {
        return (root, query, cb) -> cb.equal(root.get("createdBy"), authContext.getCurrentUsername());
    }

    private Specification<T> mergeCreatedBySpec(Specification<T> spec) {
        return (root, query, cb) -> {
            Predicate createdByPredicate = cb.equal(root.get("createdBy"), authContext.getCurrentUsername());
            if (spec == null) {
                return createdByPredicate;
            }
            Predicate userPredicate = spec.toPredicate(root, query, cb);
            return userPredicate == null ? createdByPredicate : cb.and(userPredicate, createdByPredicate);
        };
    }

    private boolean isNew(T entity) {
        if (entity instanceof BaseEntity baseEntity) {
            return baseEntity.getId() == null;
        }
        return false;
    }

    private void verifyEntity(T entity) {
        String createdBy = getCreatedBy(entity);
        if (createdBy != null && !authContext.getCurrentUsername().equals(createdBy)) {
            throw new BusinessException(CommonError.FORBIDDEN);
        }
    }

    private void verifyEntities(Iterable<? extends T> entities) {
        String currentUser = authContext.getCurrentUsername();
        for (T entity : entities) {
            String createdBy = getCreatedBy(entity);
            if (createdBy != null && !currentUser.equals(createdBy)) {
                throw new BusinessException(CommonError.FORBIDDEN);
            }
        }
    }

    private String getCreatedBy(T entity) {
        if (entity instanceof BaseEntity baseEntity) {
            return baseEntity.getCreatedBy();
        }
        return null;
    }
}
