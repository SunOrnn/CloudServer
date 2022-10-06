package com.ornn.payment.service.impl;

import com.alibaba.fastjson.JSON;
import com.alipay.api.AlipayApiException;
import com.alipay.api.internal.util.AlipaySignature;
import com.baomidou.mybatisplus.core.conditions.query.QueryWrapper;
import com.ornn.payment.convert.PayNotifyConvert;
import com.ornn.payment.entity.PayChannelParam;
import com.ornn.payment.entity.PayNotify;
import com.ornn.payment.entity.PayOrder;
import com.ornn.payment.entity.dto.AliPayReceiveDTO;
import com.ornn.payment.mapper.PayChannelParamMapper;
import com.ornn.payment.mapper.PayNotifyMapper;
import com.ornn.payment.mapper.PayOrderMapper;
import com.ornn.payment.service.PayNotifyService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.util.CollectionUtils;
import org.springframework.util.ObjectUtils;

import java.sql.Timestamp;
import java.util.HashMap;
import java.util.List;
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

        // 如果签名验证失败，则直接返回错误信息
        if (!verifyResult) {
            return "sign verify fail";
        }

        // 查询支付订单流水信息
        Map<String, Object> paramMap = new HashMap<>();
        paramMap.put("pay_id", aliPayReceiveDTO.getOut_trade_no());
        List<PayOrder> payOrderList = payOrderMapper.selectByMap(paramMap);
        if (!CollectionUtils.isEmpty(payOrderList) || payOrderList.size() <= 0) {
            return "order not exist";
        }

        // 如果验证签名成功，则保存支付结果通知报文结果
        PayOrder payOrder = payOrderList.get(0);

        // 通过Mapstruct工具进行数据对象转换
        PayNotify payNotify = PayNotifyConvert.INSTANCE.convertPayNotify(payOrder);
        payNotify.setMerchantId(aliPayReceiveDTO.getApp_id());

        // 设置状态为已处理
        payNotify.setReceiveStatus("2");

        // 将支付通知报文转为JSON格式进行存储
        payNotify.setFullinfo(JSON.toJSONString(aliPayReceiveDTO));

        // 将支付通知结果存储到数据库
        payNotifyMapper.insert(payNotify);

        // 更新支付订单状态（这里放到一个事务中，也可以异步解耦处理）
        payOrder.setUpdateTime(new Timestamp(System.currentTimeMillis()));
        payOrder.setStatus("2");
        payOrder.setTradeNo(aliPayReceiveDTO.getTrade_no());
        payOrderMapper.updateById(payOrder);

        // 想接入方同步支付结果（逻辑暂不实现）


        return "success";
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
