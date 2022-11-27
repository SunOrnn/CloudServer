package com.ornn.wallet.controller;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

@Slf4j
@RestController
@RequestMapping("/account")
@Api(value = "测试", tags = "测试API")
public class TestController {

    @ApiOperation(value = "测试")
    @PostMapping("/test1")
    public String test() {
        return "test success";
    }
}
