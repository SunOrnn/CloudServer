package com.ornn.payment.controller;

import com.ornn.payment.entity.dto.UnifiedPayDTO;
import com.ornn.payment.service.PayService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/pay")
public class PayController {

    @Autowired
    private PayService payService;

    @PostMapping("/unifiedPay")
    public ResponseEntity<?> unifiedPay(@RequestBody @Validated UnifiedPayDTO unifiedPayDTO) {
        return ResponseEntity.ok().build();
    }
}
