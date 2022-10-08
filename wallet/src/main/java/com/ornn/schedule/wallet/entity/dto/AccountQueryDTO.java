package com.ornn.schedule.wallet.entity.dto;

import lombok.Data;

@Data
public class AccountQueryDTO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 账户类型：0-现金账户；1-赠送账户
     */
    private Integer accType;
    /**
     * 账户币种（仅支持人民币、美元两种账户）
     */
    private String currency;
}
