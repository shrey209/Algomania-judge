package com.skcoder.gate_way.Services;

import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;

public class JwtUtil {
    private static final String SECRET_KEY = "supersecretkey1234567890supersecretkey!!!"; // Ensure at least 32 chars
    private static final long EXPIRATION_TIME = 1000 * 60 * 60; // 1 hour

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());

    public static String generateToken(String username, String role, String userId) {
        return Jwts.builder()
                .setSubject(username)
                .claim("role", role)  // Role stored as a simple string
                .claim("userId", userId)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

    public static Claims validateToken(String token) throws JwtException {
        try {
            return Jwts.parserBuilder()
                    .setSigningKey(key)
                    .build()
                    .parseClaimsJws(token)
                    .getBody();
        } catch (ExpiredJwtException e) {
            throw new JwtException("Token expired", e);
        } catch (MalformedJwtException | SignatureException e) {
            throw new JwtException("Invalid token", e);
        } catch (Exception e) {
            throw new JwtException("Token validation failed", e);
        }
    }

    public static String getRoleFromToken(String token) {
        Claims claims = validateToken(token);
        return claims.get("role", String.class); // Just return the role string
    }
}
