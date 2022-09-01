package com.sso;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class SsoResourceserverApplication {

	public static void main(String[] args) {
		SpringApplication.run(SsoResourceserverApplication.class, args);
	}

}
