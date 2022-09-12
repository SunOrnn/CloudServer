package com.ornn.wallet.config;

import com.ornn.wallet.client.PayMentClientFFallbackFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class PaymentConfiguration {

    @Bean
    PayMentClientFFallbackFactory payMentClientFallbackFactory() {
        return new PayMentClientFallbackFactory();
    }
}
