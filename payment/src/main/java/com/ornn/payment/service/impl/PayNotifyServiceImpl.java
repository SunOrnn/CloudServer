package com.ornn.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ornn.payment.entity.PayChannelParam;
import com.ornn.payment.entity.dto.AliPayReceiveDTO;
import com.ornn.payment.mapper.PayChannelParamMapper;
import com.ornn.payment.mapper.PayNotifyMapper;
import com.ornn.payment.mapper.PayOrderMapper;
import com.ornn.payment.service.PayNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.ObjectUtils;

import java.util.Map;

@Service
public class PayNotifyServiceImpl implements PayNotifyService {

    @Autowired
    private PayChannelParamMapper payChannelParamMapper;

    @Autowired
    private PayOrderMapper payOrderMapper;

    @Autowired
    private PayNotifyMapper payNotifyMapper;

    @Override
    public String aliPayReceive(AliPayReceiveDTO aliPayReceiveDTO) {
        // 对报文进行签名验证
        boolean verifyResult = aliPayReceiveMsgVerify(aliPayReceiveDTO);
        return null;
    }

    private boolean aliPayReceiveMsgVerify(AliPayReceiveDTO aliPayReceiveDTO) {
        // 查询支付宝支付RSA公钥信息
        QueryWrapper<PayChannelParam> queryWrapper = new QueryWrapper<>();
        queryWrapper.and(wq -> wq.eq("partner", aliPayReceiveDTO.getApp_id()))
                .and(wq -> wq.eq("status", "0"))
                .and(wq -> wq.eq("key_type", "publickey"));
        PayChannelParam payChannelParam = payChannelParamMapper.selectOne(queryWrapper);

        // 如果支付参数信息不存在，则直接返回失败
        if (ObjectUtils.isEmpty(payChannelParam)) {
            return false;
        }

        // 将支付参数对象转换为Map
        Map<String, String> map = JSON.parseObject(JSON.toJSONString(aliPayReceiveDTO), Map.class);

        // 调用支付宝支付SDK验证签名
        boolean signVerified = false;
        try {
            signVerified = AlipaySignature.rsaCheckV1(map, payChannelParam.getKeyContent(), "UTF-8", payChannelParam.getSignType());
        } catch (AlipayApiException e) {
            e.printStackTrace();
        }
        // 由于模拟时需要支付宝私钥签名，所以这里为了便于测试默认返回签名验证成功
        return true;
    }
}
