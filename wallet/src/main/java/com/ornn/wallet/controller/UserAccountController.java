package com.ornn.wallet.controller;

import com.ornn.wallet.entity.dto.AccountOpenDTO;
import com.ornn.wallet.entity.dto.AccountQueryDTO;
import com.ornn.wallet.entity.vo.AccountOpenVO;
import com.ornn.wallet.entity.vo.AccountVO;
import com.ornn.wallet.entity.ResponseResult;
import com.ornn.wallet.service.UserAccountService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * @author ORNN
 */
@Slf4j
@RestController
@RequestMapping("/account")
@Api(value = "电子钱包账户管理", tags = "电子钱包账户管理API")
public class UserAccountController {
    @Autowired
    private UserAccountService userAccountService;

    /**
     * 电子钱包开户
     * @return
     */
    @ApiOperation(value = "电子钱包开户")
    @PostMapping("/openAcc")
    public ResponseResult<AccountOpenVO> openAcc(@RequestBody @Validated AccountOpenDTO accountOpenDTO) throws IllegalAccessException {
        return ResponseResult.OK(userAccountService.openAcc(accountOpenDTO));
    }

    /**
     * 电子钱包查询
     * @return
     */
    @ApiOperation(value = "电子钱包查询")
    @GetMapping("/queryAcc")
    public ResponseResult<List<AccountVO>> queryAcc(@Validated AccountQueryDTO accountQueryDTO) {
        return ResponseResult.OK(userAccountService.queryAcc(accountQueryDTO));
    }

    @ApiOperation(value = "测试一")
    @GetMapping("/test")
    public ResponseResult<String> test() {
        return ResponseResult.OK("test success");
    }
}
