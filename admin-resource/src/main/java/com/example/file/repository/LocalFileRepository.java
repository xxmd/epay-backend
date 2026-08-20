package com.example.file.repository;

import com.example.file.domain.entity.LocalFile;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;

public interface LocalFileRepository extends JpaRepository<LocalFile, Long>, JpaSpecificationExecutor<LocalFile> {
}
