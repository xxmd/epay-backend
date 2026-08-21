package com.example.pay.repository;

import com.example.pay.domain.entity.Merchant;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import java.util.List;

public interface MerchantRepository extends JpaRepository<Merchant, Long>, JpaSpecificationExecutor<Merchant> {

    @Query("SELECT DISTINCT m FROM Merchant m JOIN m.methodList method WHERE m.enabled = true AND m.platform.enabled = true AND method.id = :methodId")
    List<Merchant> findEnabledMerchantsByMethodId(@Param("methodId") Long methodId);
}
