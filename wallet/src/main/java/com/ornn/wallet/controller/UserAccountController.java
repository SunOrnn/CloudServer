package com.ornn.wallet.controller;

import com.ornn.wallet.entity.ResponseResult;
import com.ornn.wallet.entity.dto.AccountOpenDTO;
import com.ornn.wallet.entity.dto.AccountQueryDTO;
import com.ornn.wallet.entity.vo.AccountOpenVO;
import com.ornn.wallet.entity.vo.AccountVO;
import com.ornn.wallet.service.UserAccountService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

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
    public ResponseResult<AccountOpenVO> openAcc(@RequestBody @Validated AccountOpenDTO accountOpenDTO) throws IllegalAccessException {

        return ResponseResult.OK(userAccountService.openAcc(accountOpenDTO));
    }

    /**
     * 电子钱包查询
     * @return
     */
    @GetMapping("/queryAcc")
    public ResponseResult<List<AccountVO>> queryAcc(@Validated AccountQueryDTO accountQueryDTO) {
        return ResponseResult.OK(userAccountService.queryAcc(accountQueryDTO));
    }
}
