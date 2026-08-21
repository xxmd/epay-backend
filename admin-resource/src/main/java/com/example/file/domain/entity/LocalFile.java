package com.example.file.domain.entity;

import com.example.common.domain.entity.BaseEntity;
import com.example.common.util.SpringContextHolder;
import jakarta.persistence.Entity;
import jakarta.persistence.PostRemove;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.extern.slf4j.Slf4j;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

@Getter
@Setter
@Entity
@Table(name = "res_local_file")
@Slf4j
public class LocalFile extends BaseEntity {
    private String name;
    private Long size;
    private String path;

    @PostRemove
    public void postRemove() {
        String uploadDir = SpringContextHolder.getProperty("file.upload-dir");
        if (uploadDir == null) {
            uploadDir = "./files";
        }
        try {
            Files.deleteIfExists(Paths.get(uploadDir).toAbsolutePath().normalize().resolve(path));
        } catch (IOException e) {
            log.warn("Failed to delete file: {}", path, e);
        }
    }
}
