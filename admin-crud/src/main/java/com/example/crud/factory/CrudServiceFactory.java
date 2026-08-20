package com.example.crud.factory;

import com.example.crud.service.CrudService;
import net.bytebuddy.ByteBuddy;
import net.bytebuddy.description.type.TypeDescription;
import net.bytebuddy.dynamic.loading.ClassLoadingStrategy;
import org.springframework.aop.support.AopUtils;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.config.AutowireCapableBeanFactory;
import org.springframework.context.ApplicationContext;
import org.springframework.core.ResolvableType;
import org.springframework.stereotype.Component;

import java.util.Arrays;
import java.util.List;
import java.util.Locale;
import java.util.Map;

@Component
public class CrudServiceFactory {
    @Autowired
    private ApplicationContext context;

    public CrudService findOrCreate(ResolvableType entityGeneric, ResolvableType idGeneric, ResolvableType queryConditionGeneric, ResolvableType serviceGeneric) {
        CrudService crudService = null;
        crudService = find(entityGeneric, idGeneric, queryConditionGeneric);
        if (crudService == null) {
            crudService = create(serviceGeneric, entityGeneric);
        }
        return crudService;
    }


    private CrudService find(ResolvableType entityGeneric, ResolvableType idGeneric, ResolvableType queryConditionGeneric) {
        Map<String, CrudService> serviceBeanMap = context.getBeansOfType(CrudService.class);
        for (CrudService serviceBean : serviceBeanMap.values()) {
            if (isGenericAllMatch(serviceBean, entityGeneric, idGeneric, queryConditionGeneric)) {
                return serviceBean;
            }
        }
        return null;
    }

    private boolean isGenericAllMatch(CrudService serviceBean, ResolvableType expectEntityGeneric, ResolvableType expectIdGeneric, ResolvableType expectQueryConditionGeneric) {
        Class<?> targetClass = AopUtils.getTargetClass(serviceBean);
        ResolvableType crudServiceType = ResolvableType.forClass(targetClass).as(CrudService.class);
        ResolvableType actualEntityGeneric = crudServiceType.getGeneric(0);
        ResolvableType actualIdGeneric = crudServiceType.getGeneric(1);
        ResolvableType actualQueryConditionGeneric = crudServiceType.getGeneric(2);
        return expectEntityGeneric.isAssignableFrom(actualEntityGeneric)
                && expectIdGeneric.isAssignableFrom(actualIdGeneric)
                && expectQueryConditionGeneric.isAssignableFrom(actualQueryConditionGeneric);
    }

    private CrudService create(ResolvableType serviceGeneric, ResolvableType entityGeneric) {
        try {
            Class<?> entityClass = entityGeneric.resolve();
            Class<?> serviceClass = serviceGeneric.resolve();
            String finalClassSimpleName = entityClass.getSimpleName() + serviceClass.getSimpleName();
            String fullClassName = "com.example.service.impl." + finalClassSimpleName;
            List<? extends Class<?>> genericList = Arrays.stream(serviceGeneric.getGenerics()).map(it -> it.resolve(Object.class)).toList();
            TypeDescription.Generic superType = TypeDescription.Generic.Builder
                    .parameterizedType(serviceClass, genericList)
                    .build();
            Class<?> dynamicSubclass = new ByteBuddy()
                    .subclass(superType)
                    .name(fullClassName)
                    .make()
                    .load(getClass().getClassLoader(), ClassLoadingStrategy.Default.INJECTION)
                    .getLoaded();
            AutowireCapableBeanFactory beanFactory = context.getAutowireCapableBeanFactory();
            return (CrudService) beanFactory.createBean(dynamicSubclass);
        } catch (Exception e) {
            throw new RuntimeException(String.format(Locale.US, "Auto create %sCrudService failure", entityGeneric.resolve().getSimpleName()), e);
        }
    }
}
