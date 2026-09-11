package com.example.pay.mapper;

import com.example.pay.domain.dto.CredentialDto;
import com.example.common.domain.entity.Credential;
import com.example.pay.domain.vo.CredentialVo;
import com.example.pay.domain.vo.SimpleCredentialVo;
import com.example.crud.mapper.BaseMapper;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;

import java.util.List;

@Mapper(componentModel = "spring", uses = {})
public interface CredentialMapper extends BaseMapper<Credential, CredentialVo, CredentialDto> {
    @Override
    @Mapping(target = "user", ignore = true)
    Credential toEntity(CredentialDto dto);

    SimpleCredentialVo toSimpleVo(Credential credential);

    List<SimpleCredentialVo> toSimpleVoList(List<Credential> credentialList);
}
