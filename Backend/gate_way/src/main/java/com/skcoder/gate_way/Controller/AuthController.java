package com.skcoder.gate_way.Controller;

import org.springframework.web.bind.annotation.*;

import com.skcoder.gate_way.Services.JwtService;

import org.springframework.http.ResponseCookie;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpCookie;
import org.springframework.http.HttpStatus;
import reactor.core.publisher.Mono;
import org.springframework.http.server.reactive.ServerHttpRequest;
import org.springframework.http.server.reactive.ServerHttpResponse;

@RestController
@RequestMapping("/authv1")
public class AuthController {
	
	@Autowired
	JwtService jwtService;

    @GetMapping("/test")
    public Mono<Boolean> testJwt(ServerHttpRequest request) {
        return extractJwtFromCookies(request)
            .map(jwt -> {
                System.out.println("JWT Found: " + jwt);
               return jwtService.validateToken(jwt);
            })
            .defaultIfEmpty(false)
            .doOnNext(jwtExists -> {
                if (!jwtExists) {
                    System.out.println("JWT Not Found");
                }
            });
    }

    private Mono<String> extractJwtFromCookies(ServerHttpRequest request) {
        return Mono.justOrEmpty(request.getCookies().getFirst("jwt"))
            .doOnNext(cookie -> System.out.println("Found JWT Cookie: " + cookie.getValue())) // Debugging log
            .map(HttpCookie::getValue)
            .switchIfEmpty(Mono.fromRunnable(() -> System.out.println("JWT Cookie Not Found"))); // Log when no cookie
    }

}
