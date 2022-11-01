package com.ornn.schedule.payment.controller;

import com.ornn.schedule.payment.entity.dto.AliPayReceiveDTO;
import com.ornn.schedule.payment.service.PayNotifyService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/notify")
@Api(value = "Asynchronous Payment Result Notification", tags = "异步支付结果通知API")
public class PayNotifyController {

    @Autowired
    private PayNotifyService payNotifyService;

    /**
     * 定义”支付宝异步支付结果通知“
     */
    @ApiOperation(value = "Notice of Alipay asynchronous payment results", notes = "支付宝异步支付结果通知")
    @PostMapping("/aliPayReceive")
    public String aliPayReceive(AliPayReceiveDTO aliPayReceiveDTO) {
        return payNotifyService.aliPayReceive(aliPayReceiveDTO);
    }

}
