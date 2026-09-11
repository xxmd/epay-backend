package com.example.pay.service;

import com.example.pay.domain.dto.CredentialDto;
import com.example.common.domain.entity.Credential;
import com.example.pay.domain.query.CredentialQueryCondition;
import com.example.pay.domain.vo.CredentialVo;
import com.example.pay.domain.vo.SimpleCredentialVo;
import com.example.pay.mapper.CredentialMapper;
import com.example.auth.util.AuthContext;
import com.example.common.domain.entity.User;
import com.example.common.exception.BusinessException;
import com.example.crud.service.EntityCrudService;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.security.SecureRandom;
import java.util.List;
import java.util.UUID;

import static com.example.pay.domain.enums.PayError.CREDENTIAL_NOT_FOUND;

@Service
@AllArgsConstructor
public class CredentialService extends EntityCrudService<Credential, CredentialQueryCondition, CredentialVo, CredentialDto> {
    private final CredentialMapper mapper;
    private final AuthContext authContext;

    @Override
    public Credential dtoToEntityOnCreate(CredentialDto dto) {
        Credential entity = mapper.toEntity(dto);
        entity.setAccessKey(UUID.randomUUID().toString().replace("-", ""));
        entity.setAccessSecret(generateSecret());
        User user = authContext.getCurrentUser();
        entity.setUser(user);
        return entity;
    }

    @Override
    public void update(CredentialDto dto) {
        Credential entity = repository.findById(dto.getId())
                .orElseThrow(() -> new BusinessException(CREDENTIAL_NOT_FOUND));
        entity.setName(dto.getName());
        entity.setEnabled(dto.getEnabled());
        entity.setRemark(dto.getRemark());
        repository.save(entity);
    }

    public List<SimpleCredentialVo> findAll() {
        return mapper.toSimpleVoList(repository.findAll());
    }

    private String generateSecret() {
        SecureRandom random = new SecureRandom();
        byte[] bytes = new byte[32];
        random.nextBytes(bytes);
        StringBuilder sb = new StringBuilder(64);
        for (byte b : bytes) {
            sb.append(String.format("%02x", b));
        }
        return sb.toString();
    }
}
