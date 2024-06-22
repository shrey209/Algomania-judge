package com.algomania.onlinejudge.ProblemHub;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

@SpringBootApplication
@EnableDiscoveryClient
public class ProblemHubApplication {

	public static void main(String[] args) {
		SpringApplication.run(ProblemHubApplication.class, args);
	}

}
