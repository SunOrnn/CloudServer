package com.ornn.wallet.entity.vo;

import lombok.Data;

import java.io.Serializable;

@Data
public class AccountChargeVO implements Serializable {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 充值金额，以“分”为单位
     */
    private Integer amount;
    /**
     * 充值币种
     */
    private String currency;
    /**
     * 充值订单号（由钱包系统生成）
     */
    private String orderId;
    /**
     * 支付系统唯一流水号
     */
    private String tradeNo;
    /**
     * 前端唤起支付收银台所需的额外信息
     */
    private String extraInfo;
}
