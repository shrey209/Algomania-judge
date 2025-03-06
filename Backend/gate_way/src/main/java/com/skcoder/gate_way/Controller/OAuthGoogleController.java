package com.skcoder.gate_way.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcoder.gate_way.Services.AuthService;
import com.skcoder.gate_way.Services.JwtUtil;
import com.skcoder.gate_way.Models.*;
import reactor.core.publisher.Mono;

import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth/google")
public class OAuthGoogleController {

    @Value("${google.client.id}")
    private String clientId;

    @Value("${google.client.secret}")
    private String clientSecret;

    @Value("${google.redirect.uri}")
    private String redirectUri;

    @Value("${frontend.redirect.uri}")
    private String frontendRedirectUri;

    @Autowired
    AuthService authService;

    private final WebClient webClient = WebClient.builder().build();

    @GetMapping("/login")
    public Mono<Map<String, String>> getGoogleLoginUrl() {
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&response_type=code" +
                "&scope=openid%20email%20profile";
        return Mono.just(Map.of("url", googleAuthUrl));
    }

    @GetMapping("/callback")
    public Mono<ResponseEntity<Object>> googleCallback(@RequestParam("code") String code,
                                                     @RequestParam(value = "state",required = false) String state) {
        return fetchAccessToken(code)
                .flatMap(this::fetchGoogleUserInfo)
                .flatMap(userInfo -> {
                    if (userInfo == null || !userInfo.containsKey("email")) {
                        return Mono.just(ResponseEntity.status(HttpStatus.UNAUTHORIZED).build());
                    }
                    
                    String email = (String) userInfo.get("email");
                    String username = (String) userInfo.get("name"); 
                    
                    return authService.getUserByEmail(email)
                            .switchIfEmpty(authService.registerOauthUser(username, "GOOGLE", email)) 
                            .flatMap(user -> {
                                String jwt = JwtUtil.generateToken(username, user.getRole(), user.getUserId());

                               System.out.println(jwt);
                                ResponseCookie jwtCookie = ResponseCookie.from("jwt", jwt)
                                        .httpOnly(true)
                                        .secure(true)
                                        .path("/")
                                        .maxAge(7 * 24 * 60 * 60)
                                        .sameSite("None")
                                        .build();

                                URI redirectUri = UriComponentsBuilder.fromUriString(frontendRedirectUri)
                                        .queryParam("token", jwt)
                                        .queryParam("username", email)
                                        .build()
                                        .toUri();

                                return Mono.just(
                                        ResponseEntity.status(HttpStatus.FOUND)
                                                .location(redirectUri)
                                                .header(HttpHeaders.SET_COOKIE, jwtCookie.toString()) 
                                                .build()
                                );
                            });
                })
                .doOnError(Throwable::printStackTrace);
    }

    private Mono<String> fetchAccessToken(String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        return webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("client_id=" + clientId +
                        "&client_secret=" + clientSecret +
                        "&code=" + code +
                        "&redirect_uri=" + redirectUri +
                        "&grant_type=authorization_code")
                .retrieve()
                .bodyToMono(String.class)
                .map(responseBody -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> jsonResponse = objectMapper.readValue(responseBody, Map.class);
                        return (String) jsonResponse.get("access_token");
                    } catch (Exception e) {
                        throw new RuntimeException("Error parsing Google token response", e);
                    }
                });
    }

    private Mono<Map> fetchGoogleUserInfo(String accessToken) {
        String userUrl = "https://www.googleapis.com/oauth2/v3/userinfo";

        return webClient.get()
                .uri(userUrl)
                .headers(headers -> headers.setBearerAuth(accessToken))
                .retrieve()
                .bodyToMono(Map.class);
    }
}
