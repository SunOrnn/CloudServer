package com.ornn.wallet.controller;

import com.ornn.wallet.entity.ResponseResult;
import com.ornn.wallet.entity.dto.AccountChargeDTO;
import com.ornn.wallet.entity.dto.PayNotifyDTO;
import com.ornn.wallet.entity.vo.AccountChargeVO;
import com.ornn.wallet.entity.vo.PayNotifyVO;
import com.ornn.wallet.service.UserAccountTradeService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class UserAccountTradeController {

    @Autowired
    private UserAccountTradeService userAccountTradeService;

    /**
     * “电子钱包充值”接口
     * @return
     */
    public ResponseResult<AccountChargeVO> chargeOrder(@RequestBody @Validated AccountChargeDTO accountChargeDTO) throws IllegalAccessException {
        return ResponseResult.OK(userAccountTradeService.chargeOrder(accountChargeDTO));
    }

    /**
     * ”电子钱包充值支付回调“接口
     * @return
     */
    public ResponseResult<PayNotifyVO> receivePayNotify(@RequestBody @Validated PayNotifyDTO payNotifyDTO) throws IllegalAccessException {
        return ResponseResult.OK(userAccountTradeService.receivePayNotify(payNotifyDTO));
    }
}
