package com.ornn.payment.service.impl;

import com.ornn.payment.entity.dto.UnifiedPayDTO;
import com.ornn.payment.entity.vo.UnifiedPayVO;
import com.ornn.payment.service.PayChannelService;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements PayChannelService {
    @Override
    public UnifiedPayVO pay(UnifiedPayDTO unifiedPayDTO) {
        return null;
    }
}
