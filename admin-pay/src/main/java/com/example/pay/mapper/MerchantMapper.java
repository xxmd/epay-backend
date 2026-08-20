package com.example.pay.mapper;

import com.example.crud.mapper.BaseMapper;
import com.example.pay.domain.dto.MerchantDto;
import com.example.pay.domain.entity.Merchant;
import com.example.pay.domain.entity.Platform;
import com.example.pay.domain.vo.MerchantVo;
import com.example.pay.domain.vo.SimpleMerchantVo;
import org.mapstruct.Mapper;

@Mapper(componentModel = "spring", uses = {})
public interface MerchantMapper extends BaseMapper<Merchant, MerchantVo, MerchantDto> {
    SimpleMerchantVo toSimpleVo(Platform platform);
}
