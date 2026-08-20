package com.example.file.domain.vo;

import com.example.crud.model.vo.BaseVo;
import lombok.Data;

@Data
public class LocalFileVo extends BaseVo {
    private String name;
    private Long size;
    private String path;
}
