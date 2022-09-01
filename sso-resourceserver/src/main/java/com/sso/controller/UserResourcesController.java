package com.sso.controller;

import com.sso.entity.BO.GetUserInfoBO;
import com.sso.entity.DTO.GetUserInfoDTO;
import com.sso.entity.ResponseResult;
import com.sso.service.UserResourcesService;
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
    private UserResourcesService userResourcesService;

    /**
     * 定义“用户受保护信息查询”接口
     */
    @GetMapping("/getUserInfo")
    public ResponseResult<GetUserInfoBO> getUserInfo(@Validated GetUserInfoDTO getUserInfoDTO) {
        return ResponseResult.OK(userResourcesService.getUserInfo(getUserInfoDTO));
    }


}
