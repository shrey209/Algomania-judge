package com.skcoder.gate_way.Services;

import io.jsonwebtoken.Claims;
import io.jsonwebtoken.Jwts;
import io.jsonwebtoken.security.Keys;
import org.springframework.stereotype.Service;

import javax.crypto.SecretKey;

import java.util.HashMap;
import java.util.Map;

@Service
public class JwtService {

	 private static final String SECRET_KEY = "supersecretkey1234567890supersecretkey!!!"; 
    private final SecretKey secretKey = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

   
    public boolean validateToken(String token) {
        try {
      
            Jwts.parserBuilder()
                .setSigningKey(secretKey)
                .build()
                .parseClaimsJws(token);
            return true; 
        } catch (Exception e) {
            return false; 
        }
    }

   
    public Map<String, Object> extractAllClaims(String token) {
        try {
            Claims claims = Jwts.parserBuilder()
                    .setSigningKey(secretKey)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
            return claims;
        } catch (Exception e) {
            throw new RuntimeException("Invalid token: " + e.getMessage());
        }
    }

    // Print All Token Information
    public void printTokenInfo(String token) {
        if (validateToken(token)) {
            Map<String, Object> claims = extractAllClaims(token);
            System.out.println("Token Details:");
            for (Map.Entry<String, Object> entry : claims.entrySet()) {
                System.out.println(entry.getKey() + ": " + entry.getValue());
            }
        } else {
            System.out.println("Invalid Token!");
        }
    }
}
