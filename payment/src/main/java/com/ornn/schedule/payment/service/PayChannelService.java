package com.ornn.schedule.payment.service;

import com.ornn.schedule.payment.entity.dto.UnifiedPayDTO;
import com.ornn.schedule.payment.entity.vo.UnifiedPayVO;

public interface PayChannelService {
    /**
     * 定义渠道支付业务层方法
     * @param unifiedPayDTO
     * @return
     */
    UnifiedPayVO pay(UnifiedPayDTO unifiedPayDTO);
}
