package com.ornn.wallet.entity.dto;

import com.ornn.wallet.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UnifiedPayDTO implements Serializable {
    /**
     * 接口方应用ID
     */
    @NotNull(message = "应用ID不能为空")
    private String appId;
    /**
     * 接入方支付订单ID，必须在接入方系统唯一
     */
    @NotNull(message = "支付订单ID不能为空")
    private String orderId;
    /**
     * 交易类型。用于标识具体的业务类型，如topup标志钱包充值等
     */
    @EnumValue(strValues = {"topup"})
    private String tradeType;
    /**
     * 支付渠道。0-微信支付，1-支付宝支付
     */
    @EnumValue(intValues = {0, 1})
    private Integer channel;
    /**
     * 支付产品定义，用于区分具体的渠道支付产品
     */
    private String payType;
    /**
     * 支付金额，以”分“为单位，数值必须大于0
     */
    private Double amount;
    /**
     * 支付币种，默认CNY
     */
    @EnumValue(strValues = {"CNY"})
    private String currency;
    /**
     * 商户系统唯一标识用户身份的ID
     */
    @NotNull(message = "用户ID不能为空")
    private String userId;
    /**
     * 商品标题
     */
    @NotNull(message = "商品标题不能为空")
    private String subject;
    /**
     * 商品描述信息
     */
    private String body;
    /**
     * 支付扩展信息
     */
    private Object extraInfo;
    /**
     * 用于发送”异步支付结果通知“的服务端地址
     */
    @NotNull(message = "支付通知地址不能为空")
    private String notifyUrl;
    /**
     * 同步支付结果的跳转地址
     */
    private String returnUrl;
}
