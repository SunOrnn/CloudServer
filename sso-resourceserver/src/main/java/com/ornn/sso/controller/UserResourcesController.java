package com.ornn.sso.controller;

import com.ornn.sso.entity.ResponseResult;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.validation.annotation.Validated;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@RestController
@RequestMapping("/user")
public class UserResourcesController {

    /**
     * 注入业务层（Service层）的依赖
     */
    @Autowired

    /**
     * 定义“用户受保护信息查询”接口
     */
    @GetMapping("/getUserInfo")
    public ResponseResult<GetUserInfoBO> getUserInfo(@Validated GetUserInfoDTO getUserInfoDTO) {

    }


}
