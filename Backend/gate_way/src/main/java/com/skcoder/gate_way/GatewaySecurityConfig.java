package com.skcoder.gate_way;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.web.server.ServerWebExchange;
import org.springframework.web.server.WebFilter;
import org.springframework.web.server.WebFilterChain;

import com.skcoder.gate_way.Services.JwtService;

import reactor.core.publisher.Mono;

@Configuration
public class GatewaySecurityConfig {

    @Autowired
    private JwtService jwtService;

    @Bean
    public WebFilter adminAuthFilter() {
        return (ServerWebExchange exchange, WebFilterChain chain) -> {
            String path = exchange.getRequest().getURI().getPath();
            
            if(path.startsWith("api/hello")) {
            	System.out.println("hello");
            }
     
            if (path.startsWith("/api/admin")) {
                String authHeader = exchange.getRequest().getHeaders().getFirst(HttpHeaders.AUTHORIZATION);
                System.out.println("it hit");
                if (authHeader == null || !authHeader.startsWith("Bearer ")) {
                    exchange.getResponse().setStatusCode(HttpStatus.UNAUTHORIZED);
                    return exchange.getResponse().setComplete();
                }

                String token = authHeader.substring(7); 

                if (!jwtService.validateToken(token)) {
                    exchange.getResponse().setStatusCode(HttpStatus.FORBIDDEN);
                    return exchange.getResponse().setComplete();
                }else System.out.println("validated");
            }

            return chain.filter(exchange);
        };
    }
}
