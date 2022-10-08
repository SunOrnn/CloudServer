package com.ornn.schedule.wallet.entity.vo;

import lombok.Data;


@Data
public class AccountOpenVO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 钱包系统生成的唯一账号编号
     */
    private String accNo;
    /**
     * 账户类型
     */
    private String accType;
    /**
     * 币种
     */
    private String currency;
}
