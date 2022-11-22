package com.ornn.payment.service;

import com.ornn.payment.entity.dto.UnifiedPayDTO;
import com.ornn.payment.entity.vo.UnifiedPayVO;

public interface PayService {

    /**
     * 定义”统一支付“接口
     * @param unifiedPayDTO
     * @return
     */
    UnifiedPayVO unifiedPay(UnifiedPayDTO unifiedPayDTO);
}
