package com.ornn.wallet.controller;

import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
public class UserAccountTradeController {


    public ResponseEntity<?> chargeOrder() {
        return ResponseEntity.ok().build();
    }

    public ResponseEntity<?> receivePayNotify() {
        return ResponseEntity.ok().build();
    }
}
