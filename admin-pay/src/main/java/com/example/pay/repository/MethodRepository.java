package com.example.pay.repository;

import com.example.pay.domain.entity.Method;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface MethodRepository extends JpaRepository<Method, Long>, JpaSpecificationExecutor<Method> {
}
