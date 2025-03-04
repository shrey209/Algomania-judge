package com.skcoder.gate_way.Services;
import com.github.benmanes.caffeine.cache.Cache;
import com.github.benmanes.caffeine.cache.Caffeine;
import org.springframework.stereotype.Service;

import java.util.UUID;
import java.util.concurrent.TimeUnit;
import java.security.SecureRandom;

@Service
public class CaffeineCacheService {
    private final Cache<String, String> cache;

    public CaffeineCacheService() {
        this.cache = Caffeine.newBuilder()
                .expireAfterWrite(5, TimeUnit.MINUTES) 
                .maximumSize(100) 
                .build();
    }

    public void put(String key, String value) {
        cache.put(key, value);
    }

    public String get(String key) {
        return cache.getIfPresent(key);
    }

    public void delete(String key) {
        cache.invalidate(key);
    }
    
    public String generateAndStoreOTP(String key) {
        String otp = UUID.randomUUID().toString().replaceAll("[^0-9]", "").substring(0, 6); 
        cache.put(key, otp);
        return otp;
    }
}
