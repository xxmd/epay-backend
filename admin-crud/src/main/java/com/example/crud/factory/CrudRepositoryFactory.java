package com.example.crud.factory;

import jakarta.persistence.EntityManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.data.jpa.repository.support.SimpleJpaRepository;
import org.springframework.stereotype.Component;

import java.util.Map;

@Component
public class CrudRepositoryFactory {
    @Autowired
    private ApplicationContext context;
    @Autowired
    private EntityManager entityManager;

    public SimpleJpaRepository findOrCreate(ResolvableType entityGeneric, ResolvableType idGeneric) {
        SimpleJpaRepository repository = null;
        repository = find(entityGeneric, idGeneric);
        if (repository == null) {
            repository = new SimpleJpaRepository<>(entityGeneric.resolve(), entityManager);;
        }
        return repository;
    }


    private SimpleJpaRepository find(ResolvableType entityGeneric, ResolvableType idGeneric) {
        Map<String, SimpleJpaRepository> repositoryMap = context.getBeansOfType(SimpleJpaRepository.class);
        for (SimpleJpaRepository repository : repositoryMap.values()) {
            if (isGenericAllMatch(repository, entityGeneric, idGeneric)) {
                return repository;
            }
        }
        return null;
    }

    private boolean isGenericAllMatch(SimpleJpaRepository repository, ResolvableType expectEntityGeneric, ResolvableType expectIdGeneric) {
        ResolvableType crudServiceType = ResolvableType.forClass(repository.getClass()).as(SimpleJpaRepository.class);
        ResolvableType actualEntityGeneric = crudServiceType.getGeneric(0);
        ResolvableType actualIdGeneric = crudServiceType.getGeneric(1);
        return expectEntityGeneric.isAssignableFrom(actualEntityGeneric)
                && expectIdGeneric.isAssignableFrom(actualIdGeneric);
    }
}
