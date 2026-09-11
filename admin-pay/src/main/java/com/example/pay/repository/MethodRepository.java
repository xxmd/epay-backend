package com.example.pay.repository;

import com.example.pay.domain.entity.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;

import java.util.List;

public interface MethodRepository extends JpaRepository<Method, Long>, JpaSpecificationExecutor<Method> {

    @Query("SELECT DISTINCT m FROM Method m WHERE m.enabled = true " +
            "AND m.id IN " +
            "(SELECT mtd.id FROM Platform p " +
            "JOIN p.merchantList mer " +
            "JOIN mer.methodList mtd " +
            "WHERE p.enabled = true " +
            "AND mer.enabled = true)")
    List<Method> findAvailableMethods();
}
