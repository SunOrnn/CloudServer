package com.ornn.wallet.entity.dto;

import com.ornn.wallet.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class PayNotifyDTO implements Serializable {
    /**
     * 商户支付订单号
     */
    @NotNull(message = "订单号不能为空")
    private String orderId;
    /**
     * 支付订单金额
     */
    private Integer amount;
    /**
     * 支持币种
     */
    private String currency;
    /**
     * 支付订单状态。0-待支付；1-支付中；2-支付成功；3-支付失败
     */
    @EnumValue(intValues = {2, 3}, message = "只接受支付状态为成功/失败的通知")
    private Integer payStatus;
}
