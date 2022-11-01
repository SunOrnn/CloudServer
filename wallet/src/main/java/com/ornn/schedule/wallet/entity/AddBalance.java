package com.ornn.schedule.wallet.entity;

import lombok.Builder;
import lombok.Data;

/**
 * 电子钱包余额增加实体类
 */
@Data
@Builder
public class AddBalance {
    /**
     * 用户ID
     */
    private String userId;

    /**
     * 增加金额
     */
    private Integer amount;

    /**
     * 业务类型
     */
    private String busiType;

    /**
     * 账户类型
     */
    private String accType;

    /**
     * 币种
     */
    private String currency;
}
