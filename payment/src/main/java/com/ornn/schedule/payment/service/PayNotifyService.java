package com.ornn.schedule.payment.service;

import com.ornn.schedule.payment.entity.dto.AliPayReceiveDTO;

public interface PayNotifyService {
    /**
     * 定义“支付宝支付结果通知回调”接口
     * @param aliPayReceiveDTO
     * @return
     */
    String aliPayReceive(AliPayReceiveDTO aliPayReceiveDTO);
}
