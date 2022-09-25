package com.ornn.payment.entity.dto;

import com.google.common.xml.XmlEscapers;
import com.ornn.payment.validator.EnumValue;
import lombok.Data;

import javax.validation.constraints.NotNull;
import java.io.Serializable;

@Data
public class UnifiedPayDTO implements Serializable {
    /**
     * 接入方应用ID
     */
    @NotNull(message = "应用ID不能为空")
    private String appId;

    /**
     * 接入方支付订单ID，必须在接入方系统唯一（如电子钱包系统）
     */
    @NotNull(message = "支付订单ID不能为空")
    private String orderId;

    /**
     * 交易类型。用于标识具体的业务流程，如topup表示钱包充值等
     */
    @EnumValue(strValues = {"topup"})
    private String tradeType;

    /**
     * 支付渠道。0-微信支付， 1-支付宝支付
     */
    @EnumValue(intValues = {0, 1})
    private Integer channel;

    /**
     * 支付产品定义。用于区分具体的渠道支付产品，可根据实际情况定义
     */
    private String payType;

    /**
     * 支付金额，以”分“为单位，数值必须大于0
     */
    private Integer amount;

    /**
     * 支付币种，默认为CNY
     */
    @EnumValue(strValues = {"CNY"})
    private String currency;

    /**
     * 接入方系统唯一标识用户身份的ID
     */
    @NotNull(message = "用户ID不能为空")
    private String userId;

    /**
     * 商品标题，支付所购买的商品标题
     */
    @NotNull(message = "商品标题不能为空")
    private String subject;

    /**
     * 商品描述信息
     */
    private String body;

    /**
     * 支付扩展信息，例如针对某些支付渠道的特殊请求参数的补充
     */
    private Object extraInfo;

    /**
     * 异步支付结果通知地址
     */
    @NotNull(message = "支付通知地址不能为空")
    private String notifyUrll;

    /**
     * 同步支付结果跳转地址（支付成功后同步跳转转回接入方系统界面的URL
     */
    private String returnUrl;

}
