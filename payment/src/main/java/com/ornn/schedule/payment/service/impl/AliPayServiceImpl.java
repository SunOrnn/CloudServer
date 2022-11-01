package com.ornn.schedule.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.AlipayClient;
import com.alipay.api.DefaultAlipayClient;
import com.alipay.api.request.AlipayTradePagePayRequest;
import com.ornn.schedule.payment.entity.constant.BusinessCodeEnum;
import com.ornn.schedule.payment.entity.dto.UnifiedPayDTO;
import com.ornn.schedule.payment.entity.vo.UnifiedPayVO;
import com.ornn.schedule.payment.exception.ServiceException;
import com.ornn.schedule.payment.service.PayChannelService;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
public class AliPayServiceImpl implements PayChannelService {

    /**
     * “支付网关”接口的地址
     */
    @Value("${channel.alipay.payUrl}")
    private String payUrl;

    /**
     * 支付宝应用ID
     */
    @Value("${channel.alipay.appId}")
    private String appId;

    /**
     * 支付宝应用私钥
     */
    @Value("${channel.alipay.privateKey}")
    private String privateKey;

    /**
     * 支付宝应用公钥
     */
    @Value("${channel.alipay.publicKey}")
    private String publicKey;

    /**
     * 数据格式
     */
    private String format = "json";

    /**
     * 字符格式
     */
    private String charset = "UTF-8";

    /**
     * 加密方法
     */
    private String signType = "RSA2";

    @Override
    public UnifiedPayVO pay(UnifiedPayDTO unifiedPayDTO) {
        // 获得初始化的AlipayClient
        AlipayClient alipayClient = new DefaultAlipayClient(payUrl, appId, privateKey, format, charset, publicKey, signType);

        // 创建API对应的request
        AlipayTradePagePayRequest alipayRequest = new AlipayTradePagePayRequest();

        // 在公共参数中设置同步跳转地址和异步支付结果通知地址
        alipayRequest.setReturnUrl(unifiedPayDTO.getReturnUrl());
        alipayRequest.setNotifyUrl(alipayRequest.getNotifyUrl());

        // 填充业务参数（参考具体支付产品的请求参数要求）
        BizContent bizContent = BizContent.builder().out_trade_no(String.valueOf(unifiedPayDTO.getOrderId()))
                .product_code("FAST_INSTANT_TRADE_PAY")
                .total_amount(Double.valueOf(unifiedPayDTO.getAmount()) / 100)
                .subject(unifiedPayDTO.getSubject())
                .body(unifiedPayDTO.getBody())
                .passback_params("merchantBizType%" + unifiedPayDTO.getTradeType())
                .build();

        alipayRequest.setBizContent(JSON.toJSONString(bizContent));
        // 用户支付宝页面跳转带的“form”表单信息
        String form = "";
        try {
            // 调用SDK生成支付请求参数
            form = alipayClient.pageExecute(alipayRequest).getBody();
        } catch (AlipayApiException e) {
            // 将支付渠道错误封装为系统可识别的异常码
            throw new ServiceException(BusinessCodeEnum.BUSI_CHANNEL_FAIL_2000.getCode(), BusinessCodeEnum.BUSI_CHANNEL_FAIL_2000.getDesc());
        }
        return UnifiedPayVO.builder().orderId(unifiedPayDTO.getOrderId()).extraInfo(form).build();
    }

    /**
     * 此内部类用于封装支付宝请求参数中的业务参数
     */
    @Data
    @Builder
    @NoArgsConstructor
    @AllArgsConstructor
    static class BizContent {
        private String out_trade_no;
        private String product_code;
        private Double total_amount;
        private String subject;
        private String body;
        private String passback_params;
    }
}
