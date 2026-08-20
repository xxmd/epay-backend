package com.example.pay.domain.vo;

import com.example.crud.model.vo.BaseVo;
import com.example.file.domain.vo.LocalFileVo;
import com.example.pay.domain.enums.Platform;
import lombok.Data;

@Data
public class ApplicationVo extends BaseVo {
    private LocalFileVo iconFile;

    private String name;

    private Platform platform;

    private Boolean enabled;

    private String remark;
}
