package com.skcoder.gate_way.Controller;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.util.UriComponentsBuilder;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.skcoder.gate_way.Services.AuthService;
import com.skcoder.gate_way.Services.JwtUtil;
import com.skcoder.gate_way.Models.User;
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
    private AuthService authService;

    private final WebClient webClient = WebClient.create();

    @GetMapping("/login")
    public Map<String, String> getGoogleLoginUrl() {
        String googleAuthUrl = "https://accounts.google.com/o/oauth2/auth"
                + "?client_id=" + clientId
                + "&redirect_uri=" + redirectUri
                + "&response_type=code"
                + "&scope=openid%20email%20profile";
        return Map.of("url", googleAuthUrl);
    }

    @GetMapping("/callback")
    public Mono<ResponseEntity<Object>> googleCallback(@RequestParam("code") String code, 
                                                     @RequestParam(value = "state",required = false) String state) {
        return fetchAccessToken(code)
                .flatMap(this::fetchGoogleUserInfo)
                .flatMap(userInfo -> {
                    if (userInfo == null || !userInfo.containsKey("email")) {
                        return Mono.just(ResponseEntity.status(401).build());
                    }
                    
                    String email = (String) userInfo.get("email");
                    User user = authService.registerOauthUser(email, "GOOGLE");
                    String jwt = JwtUtil.generateToken(email, user.getRoles(), user.getId());

                    String redirectPath = (state != null && !state.isEmpty()) ? state : "/";
                    URI redirectUri = UriComponentsBuilder.fromUriString(frontendRedirectUri + redirectPath)
                            .queryParam("token", jwt)
                            .queryParam("username", email)
                            .build()
                            .toUri();

                    return Mono.just(ResponseEntity.status(302).location(redirectUri).build());
                })
                .onErrorResume(e -> Mono.just(ResponseEntity.status(500).build()));
    }

    private Mono<String> fetchAccessToken(String code) {
        String tokenUrl = "https://oauth2.googleapis.com/token";

        return webClient.post()
                .uri(tokenUrl)
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .bodyValue("client_id=" + clientId
                        + "&client_secret=" + clientSecret
                        + "&code=" + code
                        + "&redirect_uri=" + redirectUri
                        + "&grant_type=authorization_code")
                .retrieve()
                .bodyToMono(String.class)
                .flatMap(response -> {
                    try {
                        ObjectMapper objectMapper = new ObjectMapper();
                        Map<String, Object> jsonResponse = objectMapper.readValue(response, Map.class);
                        return Mono.justOrEmpty((String) jsonResponse.get("access_token"));
                    } catch (Exception e) {
                        return Mono.empty();
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
