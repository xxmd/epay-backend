package com.example.common.repository;

import com.example.common.domain.entity.Credential;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

import java.util.Optional;

public interface CredentialRepository extends JpaRepository<Credential, Long>, JpaSpecificationExecutor<Credential> {
    Optional<Credential> findByAccessKey(String accessKey);
}
