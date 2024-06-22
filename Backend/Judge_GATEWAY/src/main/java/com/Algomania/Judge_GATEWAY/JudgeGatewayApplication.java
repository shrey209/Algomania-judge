package com.Algomania.Judge_GATEWAY;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class JudgeGatewayApplication {

	public static void main(String[] args) {
		SpringApplication.run(JudgeGatewayApplication.class, args);
	}

}
