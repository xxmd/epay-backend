package com.example.file.controller;

import com.example.common.model.Result;
import com.example.crud.controller.EntityCrudController;
import com.example.crud.model.annotation.PermissionPrefix;
import com.example.file.domain.dto.LocalFileDto;
import com.example.file.domain.entity.LocalFile;
import com.example.file.domain.query.LocalFileQueryCondition;
import com.example.file.domain.vo.LocalFileVo;
import com.example.file.service.LocalFileService;
import lombok.AllArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.io.IOException;

@RestController
@RequestMapping("/resource/file")
@PermissionPrefix("resource:file")
@AllArgsConstructor
public class LocalFileController extends EntityCrudController<LocalFile, LocalFileQueryCondition, LocalFileVo, LocalFileDto> {

    private final LocalFileService service;

    @PostMapping("/upload")
    public Result<LocalFileVo> upload(@RequestParam("file") MultipartFile file) throws IOException {
        return service.upload(file);
    }
}
