package com.ornn.payment.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.data.redis.connection.RedisConnectionFactory;
import org.springframework.integration.redis.util.RedisLockRegistry;

/**
 * @author: CANHUI.WANG * @create: 2022-10-06
 */
@Configuration
public class RedisLockConfiguration {

    public RedisLockRegistry redisLockConfiguration(RedisConnectionFactory redisConnectionFactory) {
        return new RedisLockRegistry(redisConnectionFactory, "payment");
    }
}
