package com.example.crud.security;

/**
 * 基于 ThreadLocal 的数据权限上下文，由 AOP 切面激活，由 DataPermissionJpaRepository 读取。
 */
public final class DataPermissionContext {

    private static final ThreadLocal<Boolean> ACTIVE = ThreadLocal.withInitial(() -> Boolean.FALSE);

    private DataPermissionContext() {
    }

    public static void activate() {
        ACTIVE.set(Boolean.TRUE);
    }

    public static void deactivate() {
        ACTIVE.remove();
    }

    public static boolean isActive() {
        return ACTIVE.get();
    }
}
