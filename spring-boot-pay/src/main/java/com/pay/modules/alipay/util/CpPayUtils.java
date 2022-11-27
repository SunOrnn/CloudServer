package com.pay.modules.alipay.util;

import com.alipay.easysdk.factory.Factory;
import com.alipay.easysdk.kernel.Config;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.stereotype.Component;

/**
 * 初始化支付
 * 爪哇笔记 https://blog.52itstyle.vip
 * @author 小柒2012
 */
@Component
@Configuration
@EnableConfigurationProperties({CpAliPayProperties.class})
public class CpPayUtils {

    private CpAliPayProperties aliPay;

    public CpPayUtils(CpAliPayProperties aliPay) {
        this.aliPay = aliPay;
        Config config = new Config();
        config.protocol = aliPay.getProtocol();
        config.gatewayHost = aliPay.getGatewayHost();
        config.signType = aliPay.getSignType();
        config.appId = aliPay.getAppId();
        /**
         * 为避免私钥随源码泄露，推荐从文件中读取私钥字符串而不是写入源码中
         */
        config.merchantPrivateKey = aliPay.getMerchantPrivateKey();
        config.alipayPublicKey = aliPay.getAliPayPublicKey();
        config.notifyUrl = aliPay.getNotifyUrl();
        config.encryptKey = aliPay.getEncryptKey();
        Factory.setOptions(config);
    }

    public CpAliPayProperties getConfig(){
        return aliPay;
    }
}