package com.ornn.wallet.controller;

import com.ornn.wallet.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author ORNN
 */
@RestController
@RequestMapping("/account")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 电子钱包开户
     * @return
     */
    @PostMapping("/openAcc")
    public ResponseEntity<?> openAcc() {

        return ResponseEntity.ok().build();
    }

    /**
     * 电子钱包查询
     * @return
     */
    @GetMapping("/queryAcc")
    public ResponseEntity<?> queryAcc() {
        return ResponseEntity.ok().build();
    }
}
