package com.ornn.wallet.entity.dto;

import com.ornn.wallet.validator.EnumValue;
import lombok.Data;

@Data
public class AccountChargeDTO {
    /**
     * 用户ID
     */
    private Long userId;
    /**
     * 充值金额，以“分”为单位
     */
    private Double amount;
    /**
     * 充值币种（仅支持人民币）
     */
    @EnumValue(strValues = {"CNY"})
    private String currency;
    /**
     * 支付类型。0-微信支付，1-支付宝支付
     */
    private Integer paymentType;
    /**
     * 是否自动续费
     */
    @EnumValue(intValues = {0, 1})
    private Integer isRenew;
}
