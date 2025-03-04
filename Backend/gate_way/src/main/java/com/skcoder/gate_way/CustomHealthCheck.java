package com.skcoder.gate_way;
import org.springframework.boot.actuate.health.Health;
import org.springframework.boot.actuate.health.HealthIndicator;
import org.springframework.stereotype.Component;

@Component
public class CustomHealthCheck implements HealthIndicator {
    @Override
    public Health health() {
      
        return Health.up().withDetail("message", "Everything is OK").build();
    }
}
