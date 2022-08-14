package com.ornn.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SsoGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoGatewayApplication.class, args);
	}

}
