package com.ornn.schedule;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@EnableDiscoveryClient
@SpringBootApplication
public class CheckScheduleApplication
{
    public static void main( String[] args )
    {
        SpringApplication.run(CheckScheduleApplication.class, args);
    }
}
