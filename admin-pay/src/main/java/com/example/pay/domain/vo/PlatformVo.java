package com.example.pay.domain.vo;

import com.example.crud.domain.vo.BaseVo;
import jakarta.persistence.OneToMany;
import lombok.Data;

import java.util.List;

@Data
public class PlatformVo extends BaseVo {
    private String name;

    private String domainName;

    private String contact;

    private Integer sort;

    private Boolean enabled;

    private String remark;

    @OneToMany(mappedBy = "platform")
    private List<SimpleMerchantVo> merchantList;
}
