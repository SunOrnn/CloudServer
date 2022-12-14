package com.ornn.payment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;

/**
 * @author: CANHUI.WANG * @create: 2022-09-27
 */
@Data
@Builder
@AllArgsConstructor
@NoArgsConstructor
@TableName("pay_notify")
public class PayNotify {
    /**
     * 主键ID
     */
    private Integer id;

    /**
     * 支付订单号
     */
    private String payId;

    /**
     * 支付渠道
     */
    private Integer channel;

    /**
     * 支付状态
     */
    private String status;

    /**
     * 支付通知原始报文信息
     */
    private String fullinfo;

    /**
     * 业务方订单号
     */
    private String orderId;

    /**
     * 报文签名验证结果。0-验证成功；1-签名验证失败
     */
    private Integer verify;

    /**
     * 渠道支付商户号
     */
    private String merchantId;

    /**
     * 接收处理状态。1-已接收；2-已处理；3-已同步至业务方
     */
    private String receiveStatus;

    /**
     * 接入方通知次数
     */
    private Integer notifyCount;

    /**
     * 接入方最近通知时间
     */
    private Timestamp notifyTime;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 创建时间
     */
    private Timestamp createTime;
}
