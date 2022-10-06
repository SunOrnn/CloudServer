package com.ornn.payment.controller;

import com.ornn.payment.entity.ResponseResult;
import com.ornn.payment.entity.dto.UnifiedPayDTO;
import com.ornn.payment.entity.vo.UnifiedPayVO;
import com.ornn.payment.service.PayService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pay")
@Api(value = "Unified Payment Interface Controller", tags = "统一支付接口API")
public class PayController {

    @Autowired
    private PayService payService;

    @ApiOperation(value = "Unified Payment Interface", notes = "统一支付接口")
    @PostMapping("/unifiedPay")
    public ResponseResult<UnifiedPayVO> unifiedPay(@RequestBody @Validated UnifiedPayDTO unifiedPayDTO) {
        return ResponseResult.OK(payService.unifiedPay(unifiedPayDTO));
    }
}
