package com.ornn.schedule.sso.client;

import com.ornn.schedule.sso.entity.ResponseResult;
import feign.hystrix.FallbackFactory;
import lombok.extern.slf4j.Slf4j;

/**
 * @author: CANHUI.WANG * @create: 2022-08-01
 */
@Slf4j
public class ResourceServerFallbackFactory implements FallbackFactory<ResourceServerClient> {

    @Override
    public ResourceServerClient create(Throwable throwable) {
        return checkPasswordDTO -> {
            log.info("资源服务调用降级逻辑处理...");
            log.error(throwable.getMessage());
            return  ResponseResult.systemException();
        };
    }
}
