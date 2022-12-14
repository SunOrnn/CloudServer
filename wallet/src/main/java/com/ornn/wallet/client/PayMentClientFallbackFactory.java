package com.ornn.wallet.client;

import com.ornn.wallet.entity.ResponseResult;
import com.ornn.wallet.entity.contant.BusinessCodeEnum;
import com.ornn.wallet.entity.dto.UnifiedPayDTO;
import com.ornn.wallet.entity.vo.UnifiedPayVO;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

@Slf4j
public class PayMentClientFallbackFactory implements FallbackFactory<PaymentClient> {
    @Override
    public PaymentClient create(Throwable throwable) {
        return new PaymentClient() {
            @Override
            public ResponseResult<UnifiedPayVO> unifiedPay(UnifiedPayDTO unifiedPayDTO) {
                log.info("支付服务调用降级逻辑处理...");
                log.error(throwable.getMessage());
                return ResponseResult.serviceException(BusinessCodeEnum.BUSI_PAY_FALL_2001.getCode(), BusinessCodeEnum.BUSI_PAY_FALL_2001.getDesc());
            }
        };
    }
}
