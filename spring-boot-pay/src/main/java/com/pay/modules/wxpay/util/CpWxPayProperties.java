package com.pay.modules.wxpay.util;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

/**
 * 企业微信
 */
@Data
@ConfigurationProperties(prefix = "cp.wx.pay")
public class CpWxPayProperties {

    private String appId;
    private String appSecret;
    private String mchId;
    private String apiKey;
    private String signType;
    private String certPath;
    private String notifyUrl;

}
