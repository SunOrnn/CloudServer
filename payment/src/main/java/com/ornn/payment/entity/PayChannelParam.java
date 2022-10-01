package com.ornn.payment.entity;

import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.sql.Timestamp;

/**
 * @author: CANHUI.WANG * @create: 2022-09-27
 */
@Data
@TableName("pay_channel_param")
public class PayChannelParam {
    /**
     * 主键
     */
    private Integer id;

    /**
     * 具体的支付渠道账号
     */
    private String partner;

    /**
     * 报文签名类型
     */
    private String signType;

    /**
     * 密钥类型
     */
    private String keyType;

    /**
     * 证书文本内容
     */
    private String keyContent;

    /**
     * 证书到期时间
     */
    private Timestamp expireTime;

    /**
     * 状态。0-可用，1-不可用
     */
    private String status;

    /**
     * 更新时间
     */
    private Timestamp updateTime;

    /**
     * 创建时间
     */
    private Timestamp createTime;

    /**
     * 备注信息
     */
    private String remark;
}
