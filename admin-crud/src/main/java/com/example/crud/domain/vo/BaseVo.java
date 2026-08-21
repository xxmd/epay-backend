package com.example.crud.domain.vo;

import lombok.Getter;
import lombok.Setter;

import java.util.Date;

@Getter
@Setter
public class BaseVo {
    private Long id;

    private Date createdDate;

    private String createdBy;

    private Date modifiedDate;

    private String modifiedBy;
}
