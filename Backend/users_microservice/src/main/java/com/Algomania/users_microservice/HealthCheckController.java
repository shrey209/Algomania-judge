package com.Algomania.users_microservice;

import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import reactor.core.publisher.Mono;

import java.util.Map;

@RestController
@RequestMapping("/health")
public class HealthCheckController {

    @GetMapping
    public Mono<Map<String, String>> health() {
        return Mono.just(Map.of("status", "UP"));
    }
}
