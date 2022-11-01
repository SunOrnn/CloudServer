package com.ornn.schedule.wallet.entity.vo;

import lombok.Builder;
import lombok.Data;

import java.io.Serializable;

@Data
@Builder
public class UnifiedPayVO implements Serializable {
    /**
     * 商品支付订单号
     */
    private String orderId;
    /**
     * 由第三方支付渠道生成的预支付订单
     */
    private String tradeNo;
    /**
     * 支付订单的金额
     */
    private Double amount;
    /**
     * 支付币种
     */
    private String currency;
    /**
     * 支付渠道的编码
     */
    private String channel;
    /**
     * 特殊支付场景所需要传递的额外支付信息
     */
    private String extraInfo;
    /**
     * 支付订单状态。0-待支付；1-支付中、；2-支付成功；3-支付失败
     */
    private Integer payStatus;
}
