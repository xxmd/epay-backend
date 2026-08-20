package com.example.file.domain.dto;

import com.example.crud.model.dto.BaseDto;
import lombok.Data;

@Data
public class LocalFileDto extends BaseDto {
    private String name;
    private Long size;
    private String path;
}
