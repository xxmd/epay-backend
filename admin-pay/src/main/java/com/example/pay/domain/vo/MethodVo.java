package com.example.pay.domain.vo;

import com.example.crud.domain.vo.BaseVo;
import lombok.Data;

@Data
public class MethodVo extends BaseVo {
    private String label;

    private String value;

    private Boolean enabled;
}
