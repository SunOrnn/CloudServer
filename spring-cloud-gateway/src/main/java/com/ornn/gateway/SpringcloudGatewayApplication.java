package com.ornn.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class SpringcloudGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(SpringcloudGatewayApplication.class, args);
	}

}
