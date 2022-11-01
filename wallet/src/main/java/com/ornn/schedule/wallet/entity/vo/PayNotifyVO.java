package com.ornn.schedule.wallet.entity.vo;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class PayNotifyVO {
    /**
     * 接受处理状态。success-成功；fail-失败
     */
    private String result;
}
