package com.ornn.wallet.client;

import com.ornn.wallet.config.PaymentConfiguration;
import com.ornn.wallet.entity.ResponseResult;
import com.ornn.wallet.entity.dto.UnifiedPayDTO;
import com.ornn.wallet.entity.vo.UnifiedPayVO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;

@FeignClient(value = "payment", configuration = PaymentConfiguration.class, fallback = PayMentClientFallbackFactory.class)
public interface PaymentClient {

    /**
     * ”统一支付“接口
     * @param unifiedPayDTO
     * @return
     */
    @PostMapping("/pay/unifiedPay")
    public ResponseResult<UnifiedPayVO> unifiedPay(@RequestBody @Validated UnifiedPayDTO unifiedPayDTO);
}
