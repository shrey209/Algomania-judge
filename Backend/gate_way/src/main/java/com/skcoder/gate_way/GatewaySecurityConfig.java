package com.skcoder.gate_way;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;
import org.springframework.web.server.ResponseStatusException;
import com.skcoder.gate_way.Services.JwtService;
import reactor.core.publisher.Mono;
import java.util.Map;

@Configuration
public class GatewaySecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Bean
    public WebFilter adminAuthFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            System.out.println("[DEBUG] WebFilter invoked for path: " + path);

            
            if (path.equals("/Algomania/problem/filter")) {
            	System.out.println("invoked");
                String token = extractTokenFromCookie(exchange);
                if (token != null) {
                    Map<String, Object> claims = jwtService.extractAllClaims(token);
                    if (claims.containsKey("userId")) {
                        String userId = String.valueOf(claims.get("userId"));
                        System.out.println("[DEBUG] Authenticated User ID: " + userId);
                        
                        ServerWebExchange modifiedExchange = exchange.mutate()
                            .request(builder -> builder.header("X-User-ID", userId))
                            .build();
                        return chain.filter(modifiedExchange);
                    }
                }
                return chain.filter(exchange); // Proceed without modification if no token is found
            }
            
            // Only apply authentication for paths starting with "/users/getuser"
            if (!path.startsWith("/users/")) {
                System.out.println("[DEBUG] Skipping JWT check for path: " + path);
                return chain.filter(exchange);
            }

            System.out.println("[DEBUG] JWT check required for path: " + path);
            
            String token = extractTokenFromCookie(exchange);
            if (token == null) {
                System.out.println("[ERROR] Missing JWT cookie");
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid or missing token"));
            }
            
            System.out.println("[DEBUG] Extracted Token from Cookie: " + token);
            
            if (!jwtService.validateToken(token)) {
                System.out.println("[ERROR] Invalid Token");
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token"));
            }

            Map<String, Object> claims = jwtService.extractAllClaims(token);
            if (!claims.containsKey("userId")) {
                System.out.println("[ERROR] Token does not contain userId");
                return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "Invalid token claims"));
            }

            String userId = String.valueOf(claims.get("userId"));
            System.out.println("[DEBUG] Authenticated User ID: " + userId);

            ServerWebExchange modifiedExchange = exchange.mutate()
                .request(builder -> builder.header("X-User-ID", userId))
                .build();
            System.out.println("[DEBUG] Final Headers Before Forwarding: " + modifiedExchange.getRequest().getHeaders());

            return chain.filter(modifiedExchange);
        };
    }

    private String extractTokenFromCookie(ServerWebExchange exchange) {
        if (exchange.getRequest().getCookies().containsKey("jwt")) {
            return exchange.getRequest().getCookies().getFirst("jwt").getValue();
        } else {
            System.out.println("[ERROR] JWT Cookie not found");
            return null;
        }
    }
    

}
