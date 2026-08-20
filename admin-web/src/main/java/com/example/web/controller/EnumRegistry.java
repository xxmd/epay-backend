package com.example.web.controller;

import com.example.common.model.annotation.ExportEnum;
import com.example.common.model.enums.WithLabelEnum;
import jakarta.annotation.PostConstruct;
import org.springframework.beans.factory.annotation.AnnotatedBeanDefinition;
import org.springframework.beans.factory.config.BeanDefinition;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.ClassPathScanningCandidateComponentProvider;
import org.springframework.core.type.filter.AnnotationTypeFilter;
import org.springframework.stereotype.Component;

import java.util.*;
import java.util.concurrent.ConcurrentHashMap;

@Component
public class EnumRegistry {

    private final ApplicationContext applicationContext;
    private final Map<String, List<Map<String, String>>> enumMap = new ConcurrentHashMap<>();

    // 注入 ApplicationContext 用来动态获取启动类包名
    public EnumRegistry(ApplicationContext applicationContext) {
        this.applicationContext = applicationContext;
    }

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

        // 自动获取启动类（@SpringBootApplication）所在的根包路径
        String scanPackage = resolveBasePackage();

        for (BeanDefinition beanDef : scanner.findCandidateComponents(scanPackage)) {
            try {
                Class<?> clazz = Class.forName(beanDef.getBeanClassName());
                if (clazz.isEnum()) {
                    collectEnum(clazz);
                }
            } catch (ClassNotFoundException e) {
                throw new RuntimeException("注册枚举失败，找不到类: " + beanDef.getBeanClassName(), e);
            }
        }
    }

    /**
     * 动态推断根包名：找到带有 @SpringBootApplication 的启动类所在包名
     */
    private String resolveBasePackage() {
        Map<String, Object> annotatedBeans = applicationContext.getBeansWithAnnotation(SpringBootApplication.class);
        if (!annotatedBeans.isEmpty()) {
            // 获取启动类的包名
            Class<?> mainClass = annotatedBeans.values().iterator().next().getClass();
            return mainClass.getPackageName();
        }
        // 兜底方案：如果没找到启动类，回退到当前类所在的根包或顶级包
        return this.getClass().getPackageName();
    }

    private void collectEnum(Class<?> clazz) {
        ExportEnum annotation = clazz.getAnnotation(ExportEnum.class);
        if (annotation == null) return;

        String name = annotation.name().isEmpty() ? clazz.getSimpleName() : annotation.name();

        List<Map<String, String>> items = new ArrayList<>();
        for (Object constant : clazz.getEnumConstants()) {
            Enum<?> e = (Enum<?>) constant;
            Map<String, String> item = new LinkedHashMap<>();
            item.put("value", e.name());

            if (constant instanceof WithLabelEnum labeled) {
                item.put("label", labeled.getLabel());
            } else {
                item.put("label", e.name());
            }
            items.add(item);
        }
        enumMap.put(name, items);
    }

    public Map<String, List<Map<String, String>>> getAll() {
        return Collections.unmodifiableMap(enumMap);
    }

    public List<Map<String, String>> get(String name) {
        return enumMap.get(name);
    }
}