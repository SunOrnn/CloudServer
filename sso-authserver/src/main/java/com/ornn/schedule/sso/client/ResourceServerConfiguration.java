package com.ornn.schedule.sso.client;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

/**
 * @author: CANHUI.WANG * @create: 2022-08-01
 */

@Configuration
public class ResourceServerConfiguration {

    @Bean
    ResourceServerFallbackFactory resourceServerFallbackFactory() {
        return new ResourceServerFallbackFactory();
    }
}
