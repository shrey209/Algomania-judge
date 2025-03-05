package com.skcoder.gate_way.Controller;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.http.*;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.web.server.ServerWebExchange;
import com.skcoder.gate_way.Models.User;
import com.skcoder.gate_way.Services.AuthService;
import com.skcoder.gate_way.Services.JwtUtil;
import reactor.core.publisher.Mono;
import java.net.URI;
import java.util.Map;

@RestController
@RequestMapping("/auth/github")
public class OAuthController {

    private final AuthService authService;
    private final WebClient webClient;

    @Value("${github.client.id}")
    private String clientId;

    @Value("${github.client.secret}")
    private String clientSecret;

    @Value("${github.redirect.uri}")
    private String redirectUri;

    @Value("${frontend.redirect.uri}")
    private String frontendRedirectUri;

    public OAuthController(AuthService authService, WebClient.Builder webClientBuilder) {
        this.authService = authService;
        this.webClient = webClientBuilder.baseUrl("https://github.com").build();
    }

    @GetMapping("/login")
    public Map<String, String> getGitHubLoginUrl() {
        String githubAuthUrl = "https://github.com/login/oauth/authorize" +
                "?client_id=" + clientId +
                "&redirect_uri=" + redirectUri +
                "&scope=user";
        return Map.of("url", githubAuthUrl);
    }

    @GetMapping("/callback")
    public Mono<ResponseEntity<Object>> githubCallback(@RequestParam("code") String code, ServerWebExchange exchange) {
        return fetchAccessToken(code)
            .flatMap(this::fetchGitHubUserInfo)
            .flatMap(userInfo -> {
                if (userInfo == null || !userInfo.containsKey("login")) {
                    return Mono.just(ResponseEntity.badRequest().build());
                }

                String username = (String) userInfo.get("login");
                return authService.getUserByUsername(username, "GITHUB")
                    .switchIfEmpty(authService.registerOauthUser(username,"GITHUB", ""))
                    .flatMap(user -> {
                        String jwt = JwtUtil.generateToken(username, user.getRole(), user.getId());
                        System.out.println(jwt);
                        exchange.getResponse().addCookie(ResponseCookie.from("jwt", jwt)
                                .httpOnly(true)
                                .secure(true)
                                .path("/")
                                .maxAge(7 * 24 * 60 * 60)
                                .sameSite("None")
                                .build());

                        return Mono.just(ResponseEntity.status(HttpStatus.FOUND)
                                .location(URI.create(frontendRedirectUri))
                                .build());
                    });
            })
            .doOnError(Throwable::printStackTrace);
    }

    private Mono<String> fetchAccessToken(String code) {
        return webClient.post()
                .uri("/login/oauth/access_token")
                .contentType(MediaType.APPLICATION_FORM_URLENCODED)
                .accept(MediaType.APPLICATION_JSON)
                .bodyValue("client_id=" + clientId + "&client_secret=" + clientSecret + "&code=" + code + "&redirect_uri=" + redirectUri)
                .retrieve()
                .bodyToMono(Map.class)
                .map(jsonResponse -> (String) jsonResponse.get("access_token"));
    }

    private Mono<Map> fetchGitHubUserInfo(String accessToken) {
        return webClient.mutate().baseUrl("https://api.github.com").build()
                .get()
                .uri("/user")
                .header(HttpHeaders.AUTHORIZATION, "Bearer " + accessToken)
                .accept(MediaType.APPLICATION_JSON)
                .retrieve()
                .bodyToMono(Map.class);
    }
}
