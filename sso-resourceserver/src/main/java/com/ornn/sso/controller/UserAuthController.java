package com.ornn.sso.controller;

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

    /**
     * 定义“登录密码验证”接口
     */
    @PostMapping("/checkPassWord")
    public ResponseBody<CheckPassWordBo> checkPassWord(@RequestBody @Validated CheckPassWordDTO checkPassWordDTO) {

    }

}