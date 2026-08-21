package com.example.web.service;

import com.example.common.domain.annotation.ExportEnum;
import com.example.common.domain.enums.WithLabelEnum;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Service;

import java.util.*;

@Service
@RequiredArgsConstructor
public class EnumService {

    private final ApplicationContext applicationContext;
    private Map<String, List<Map<String, String>>> enumMap = Map.of();

    @PostConstruct
    public void init() {
        ClassPathScanningCandidateComponentProvider scanner =
                new ClassPathScanningCandidateComponentProvider(false) {
                    @Override
                    protected boolean isCandidateComponent(AnnotatedBeanDefinition beanDefinition) {
                        return beanDefinition.getMetadata().isIndependent();
                    }
                };
        scanner.addIncludeFilter(new AnnotationTypeFilter(ExportEnum.class));

        Map<String, List<Map<String, String>>> map = new HashMap<>();
        for (BeanDefinition beanDef : scanner.findCandidateComponents(resolveBasePackage())) {
            try {
                Class<?> clazz = Class.forName(beanDef.getBeanClassName());
                if (clazz.isEnum()) {
                    collectEnum(clazz, map);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("注册枚举失败，找不到类: " + beanDef.getBeanClassName(), e);
            }
        }
        this.enumMap = Collections.unmodifiableMap(map);
    }

    private String resolveBasePackage() {
        Map<String, Object> beans = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
        if (!beans.isEmpty()) {
            return beans.values().iterator().next().getClass().getPackageName();
        }
        return getClass().getPackageName();
    }

    private void collectEnum(Class<?> clazz, Map<String, List<Map<String, String>>> map) {
        ExportEnum annotation = clazz.getAnnotation(ExportEnum.class);
        String name = annotation.name().isEmpty() ? clazz.getSimpleName() : annotation.name();
        List<Map<String, String>> items = new ArrayList<>();
        for (Object constant : clazz.getEnumConstants()) {
            Enum<?> e = (Enum<?>) constant;
            Map<String, String> item = new LinkedHashMap<>();
            item.put("value", e.name());
            item.put("label", constant instanceof WithLabelEnum labeled ? labeled.getLabel() : e.name());
            items.add(item);
        }
        map.put(name, items);
    }

    public List<Map<String, String>> get(String name) {
        return enumMap.get(name);
    }
}
