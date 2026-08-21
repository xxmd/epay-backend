package com.example.pay.service;

import com.example.common.exception.BusinessException;
import com.example.crud.service.EntityCrudService;
import com.example.pay.domain.dto.MerchantDto;
import com.example.pay.domain.entity.Merchant;
import com.example.pay.domain.enums.PayError;
import com.example.pay.domain.query.MerchantQueryCondition;
import com.example.pay.domain.vo.MerchantVo;
import com.example.pay.repository.MerchantRepository;
import com.example.pay.repository.MethodRepository;
import com.example.pay.repository.PlatformRepository;
import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

import java.util.Comparator;
import java.util.List;
import java.util.Objects;
import java.util.concurrent.ThreadLocalRandom;

@Service
@AllArgsConstructor
public class MerchantService extends EntityCrudService<Merchant, MerchantQueryCondition, MerchantVo, MerchantDto> {
    private final PlatformRepository platformRepository;
    private final MethodRepository methodRepository;
    private final MerchantRepository merchantRepository;

    @Override
    public Merchant dtoToEntity(MerchantDto merchantDto) {
        Merchant merchant = super.dtoToEntity(merchantDto);
        merchant.setPlatform(platformRepository.getReferenceById(merchantDto.getPlatformId()));
        merchant.setMethodList(methodRepository.findAllById(merchantDto.getMethodIdSet()));
        return merchant;
    }

    /**
     * 根据支付方式选择最合适的商户：
     * <ol>
     *   <li>商户与其所属平台均启用，且商户已绑定该支付方式；</li>
     *   <li>先按平台 sort、再按商户 sort 升序（sort 为空视为最低优先级，排在最后）；</li>
     *   <li>取排序最靠前的一批（平台 sort 与商户 sort 均为最小值），随机挑选一个，用于分散支付流量。</li>
     * </ol>
     */
    public Merchant selectBestMerchantByMethodId(Long methodId) {
        List<Merchant> merchants = merchantRepository.findEnabledMerchantsByMethodId(methodId);
        if (merchants.isEmpty()) {
            throw new BusinessException(PayError.NO_AVAILABLE_MERCHANT);
        }

        List<Merchant> sorted = merchants.stream()
                .sorted(Comparator
                        .comparing((Merchant m) -> m.getPlatform() == null ? null : m.getPlatform().getSort(),
                                Comparator.nullsLast(Integer::compareTo))
                        .thenComparing(Merchant::getSort, Comparator.nullsLast(Integer::compareTo)))
                .toList();

        Merchant first = sorted.get(0);
        Integer bestPlatformSort = first.getPlatform() == null ? null : first.getPlatform().getSort();
        Integer bestMerchantSort = first.getSort();

        List<Merchant> topBatch = sorted.stream()
                .filter(m -> Objects.equals(m.getPlatform() == null ? null : m.getPlatform().getSort(), bestPlatformSort)
                        && Objects.equals(m.getSort(), bestMerchantSort))
                .toList();

        return topBatch.get(ThreadLocalRandom.current().nextInt(topBatch.size()));
    }
}
