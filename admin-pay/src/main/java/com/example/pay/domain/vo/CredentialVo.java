package com.example.pay.domain.vo;

import com.example.crud.domain.vo.BaseVo;
import lombok.Getter;
import lombok.Setter;

@Getter
@Setter
public class CredentialVo extends BaseVo {
    private String name;

    private String accessKey;

    private String accessSecret;

    private Boolean enabled;

    private String remark;

    private Long merchantId;

    private UserVo user;

    @Getter
    @Setter
    public static class UserVo {
        private Long id;
        private String username;
    }
}
