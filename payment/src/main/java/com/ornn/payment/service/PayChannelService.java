package com.ornn.payment.service;

import com.ornn.payment.entity.dto.UnifiedPayDTO;
import com.ornn.payment.entity.vo.UnifiedPayVO;

public interface PayChannelService {
    /**
     * 定义渠道支付业务层方法
     * @param unifiedPayDTO
     * @return
     */
    UnifiedPayVO pay(UnifiedPayDTO unifiedPayDTO);
}
