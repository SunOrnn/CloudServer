package com.ornn.wallet.config;

import com.ornn.wallet.client.PayMentClientFallbackFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    PayMentClientFallbackFactory payMentClientFallbackFactory() {
        return new PayMentClientFallbackFactory();
    }
}
