package com.sso.controller;

import com.sso.entity.BO.CheckPassWordBO;
import com.sso.entity.DTO.CheckPassWordDTO;
import com.sso.entity.ResponseResult;
import com.sso.service.UserAuthService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/auth")
public class UserAuthController {
    /**
     * 业务层（Service层）的依赖
     */
    @Autowired
    UserAuthService userAuthService;

    /**
     * 定义“登录密码验证”接口
     */
    @PostMapping("/checkPassWord")
    public ResponseResult<CheckPassWordBO> checkPassWord(@RequestBody @Validated CheckPassWordDTO checkPassWordDTO) {
        return ResponseResult.OK(userAuthService.checkPassWord(checkPassWordDTO));
    }

}