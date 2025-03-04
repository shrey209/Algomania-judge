package com.skcoder.gate_way.Services;



import io.jsonwebtoken.*;
import io.jsonwebtoken.security.Keys;
import java.security.Key;
import java.util.Date;
import java.util.Set;
import java.util.stream.Collectors;

import com.skcoder.gate_way.Models.Role;



public class JwtUtil {
    private static final String SECRET_KEY = "supersecretkey1234567890supersecretkey"; 
    private static final long EXPIRATION_TIME = 1000 * 60 * 60;

    private static final Key key = Keys.hmacShaKeyFor(SECRET_KEY.getBytes());


    public static String generateToken(String username, Set<Role> roles,Long userid) {
      
        String roleString = roles.stream()
                                 .map(Enum::name)
                                 .collect(Collectors.joining(",")); 

        return Jwts.builder()
                .setSubject(username)
                .claim("roles", roleString) 
                .claim("userid", userid)
                .setIssuedAt(new Date())
                .setExpiration(new Date(System.currentTimeMillis() + EXPIRATION_TIME))
                .signWith(key, SignatureAlgorithm.HS256)
                .compact();
    }

   
    public static Claims validateToken(String token) throws JwtException {
        return Jwts.parserBuilder()
                .setSigningKey(key)
                .build()
                .parseClaimsJws(token)
                .getBody();
    }


    public static Set<Role> getRolesFromToken(String token) {
        Claims claims = validateToken(token);
        String roleString = claims.get("roles", String.class);
        
        return Set.of(roleString.split(",")).stream()
                   .map(Role::valueOf)
                   .collect(Collectors.toSet());
    }
}
