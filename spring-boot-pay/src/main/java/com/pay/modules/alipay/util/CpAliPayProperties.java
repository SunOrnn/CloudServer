package com.pay.modules.alipay.util;
import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

@Data
@ConfigurationProperties(prefix = "cp.ali.pay")
public class CpAliPayProperties {

    private String protocol;
    private String gatewayHost;
    private String signType;
    private String appId;
    private String merchantPrivateKey;
    private String aliPayPublicKey;
    private String notifyUrl;
    private String encryptKey;
}