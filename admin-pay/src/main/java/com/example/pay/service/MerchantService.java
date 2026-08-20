package com.example.pay.service;

import com.example.crud.service.EntityCrudService;
import com.example.pay.domain.dto.MerchantDto;
import com.example.pay.domain.entity.Merchant;
import com.example.pay.domain.query.MerchantQueryCondition;
import com.example.pay.domain.vo.MerchantVo;
import com.example.pay.repository.MethodRepository;
import com.example.pay.repository.PlatformRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class MerchantService extends EntityCrudService<Merchant, MerchantQueryCondition, MerchantVo, MerchantDto> {
    private final PlatformRepository platformRepository;
    private final MethodRepository methodRepository;

    @Override
    public Merchant dtoToEntity(MerchantDto merchantDto) {
        Merchant merchant = super.dtoToEntity(merchantDto);
        merchant.setPlatform(platformRepository.getReferenceById(merchantDto.getPlatformId()));
        merchant.setMethodList(methodRepository.findAllById(merchantDto.getMethodIdSet()));
        return merchant;
    }
}
