package com.example.file.service;

import com.example.crud.service.EntityCrudService;
import com.example.file.domain.dto.LocalFileDto;
import com.example.file.domain.entity.LocalFile;
import com.example.file.domain.query.LocalFileQueryCondition;
import com.example.file.domain.vo.LocalFileVo;
import jakarta.annotation.PostConstruct;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDate;
import java.time.LocalTime;
import java.time.format.DateTimeFormatter;

@Service
@RequiredArgsConstructor
public class LocalFileService extends EntityCrudService<LocalFile, LocalFileQueryCondition, LocalFileVo, LocalFileDto> {

    private static final int MAX_NAME_LENGTH = 200;
    private static final DateTimeFormatter TIME_SUFFIX_FMT = DateTimeFormatter.ofPattern("HHmmssSSS");

    @Value("${file.upload-dir:./files}")
    private String uploadDir;

    private Path uploadPath;

    @PostConstruct
    public void init() {
        uploadPath = Paths.get(uploadDir).toAbsolutePath().normalize();
    }

    public LocalFileVo upload(MultipartFile file) throws IOException {
        String originalName = file.getOriginalFilename();
        if (originalName == null || originalName.isBlank()) {
            originalName = "unnamed";
        }

        String[] parsed = parseFileName(originalName);
        String nameWithoutExt = parsed[0];
        String ext = parsed[1];

        String datePath = LocalDate.now().format(DateTimeFormatter.ofPattern("yyyy/MM/dd"));
        Path dir = uploadPath.resolve(datePath);
        Files.createDirectories(dir);

        String storageName = resolveStorageName(dir, nameWithoutExt, ext);

        String relativePath = datePath + "/" + storageName;
        file.transferTo(dir.resolve(storageName).toFile());

        LocalFile entity = new LocalFile();
        entity.setName(nameWithoutExt + ext);
        entity.setSize(file.getSize());
        entity.setPath(relativePath);
        repository.save(entity);

        LocalFileVo vo = mapper.toVo(entity);
        return vo;
    }

    private String[] parseFileName(String originalName) {
        String nameWithoutExt;
        String ext;
        int dotIndex = originalName.lastIndexOf('.');
        if (dotIndex > 0) {
            nameWithoutExt = originalName.substring(0, dotIndex);
            ext = originalName.substring(dotIndex);
        } else {
            nameWithoutExt = originalName;
            ext = "";
        }

        if (nameWithoutExt.length() > MAX_NAME_LENGTH) {
            nameWithoutExt = nameWithoutExt.substring(0, MAX_NAME_LENGTH);
        }
        return new String[]{nameWithoutExt, ext};
    }

    private String resolveStorageName(Path dir, String nameWithoutExt, String ext) {
        String storageName = nameWithoutExt + ext;
        while (Files.exists(dir.resolve(storageName))) {
            String timeSuffix = LocalTime.now().format(TIME_SUFFIX_FMT);
            storageName = nameWithoutExt + "_" + timeSuffix + ext;
        }
        return storageName;
    }
}
