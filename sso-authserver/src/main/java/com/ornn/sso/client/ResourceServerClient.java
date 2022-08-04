package com.ornn.sso.client;

import com.ornn.sso.entity.ResponseResult;
import com.ornn.sso.entity.bo.CheckPasswordBO;
import com.ornn.sso.entity.dto.CheckPasswordDTO;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.security.oauth2.config.annotation.web.configuration.ResourceServerConfiguration;
import org.springframework.web.bind.annotation.PostMapping;

/**
 * @author: CANHUI.WANG * @create: 2022-07-29
 */

@FeignClient(value = "sso-resourceserver", configuration = ResourceServerConfiguration.class, fallbackFactory = ResourceServerFallbackFactory.class)
public interface ResourceServerClient {
    /**
     * 登录密码验证接口
     * @param checkPasswordDTO
     * @return
     */
    @PostMapping("/auth/checkPassword")
    ResponseResult<CheckPasswordBO> checkPassWord(CheckPasswordDTO checkPasswordDTO);
}
