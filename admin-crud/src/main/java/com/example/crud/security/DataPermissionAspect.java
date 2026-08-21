package com.example.crud.security;

import com.example.crud.domain.annotation.IgnoreDataPermission;
import com.example.crud.domain.annotation.RequireCreatedBy;
import jakarta.annotation.PostConstruct;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.aspectj.lang.reflect.MethodSignature;
import org.springframework.aop.support.AopUtils;
import org.springframework.core.annotation.AnnotationUtils;
import org.springframework.stereotype.Component;

import java.lang.reflect.Method;

/**
 * 拦截标注了 @RequireCreatedBy 的服务方法，在执行期间激活 DataPermissionContext。
 * 底层 DataPermissionJpaRepository 读取该上下文，自动过滤/校验 createdBy。
 */
@Slf4j
@Aspect
@Component
public class DataPermissionAspect {
    /**
     * 仅使用静态可判定的 execution 切点，覆盖 com.example 下所有 service 包中的 public 方法。
     * <p>
     * 不使用 @target/@this：它们是运行时判定，Spring AOP 在启动阶段无法静态排除 bean，
     * 会对所有 bean（包括 final 的 TomcatServletWebServerAutoConfiguration）尝试创建 CGLIB 代理，
     * 导致启动失败（Cannot subclass final class）。
     * <p>
     * 是否真正需要数据权限，在通知内根据“目标类/实际方法”上的 @RequireCreatedBy 判断：
     * 注解标在子类上时，继承自 CrudService 的 findAll/create/update/delete 同样生效。
     */
    @Pointcut("execution(public * com.example..service..*.*(..))")
    public void serviceMethod() {
    }

    @Around("serviceMethod()")
    public Object checkRequireCreatedBy(ProceedingJoinPoint joinPoint) throws Throwable {
        if (!isDataPermissionRequired(joinPoint)) {
            return joinPoint.proceed();
        }

        log.debug("DataPermissionAspect intercepting: {}", joinPoint.getSignature());
        boolean alreadyActive = DataPermissionContext.isActive();
        if (!alreadyActive) {
            DataPermissionContext.activate();
        }
        try {
            return joinPoint.proceed();
        } finally {
            if (!alreadyActive) {
                DataPermissionContext.deactivate();
            }
        }
    }

    private boolean isDataPermissionRequired(ProceedingJoinPoint joinPoint) {
        Object target = joinPoint.getTarget();
        if (target == null) {
            return false;
        }
        Class<?> targetClass = AopUtils.getTargetClass(target);
        Method method = ((MethodSignature) joinPoint.getSignature()).getMethod();
        Method mostSpecificMethod = AopUtils.getMostSpecificMethod(method, targetClass);

        // 方法级注解优先于类级注解
        if (AnnotationUtils.findAnnotation(mostSpecificMethod, IgnoreDataPermission.class) != null
                || AnnotationUtils.findAnnotation(method, IgnoreDataPermission.class) != null) {
            return false;
        }
        if (AnnotationUtils.findAnnotation(mostSpecificMethod, RequireCreatedBy.class) != null
                || AnnotationUtils.findAnnotation(method, RequireCreatedBy.class) != null) {
            return true;
        }

        // 类级别：目标类（含父类）上标注 @IgnoreDataPermission / @RequireCreatedBy
        if (AnnotationUtils.findAnnotation(targetClass, IgnoreDataPermission.class) != null) {
            return false;
        }
        return AnnotationUtils.findAnnotation(targetClass, RequireCreatedBy.class) != null;
    }
}
