package com.skcoder.gate_way.Services;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;
import org.springframework.web.reactive.function.client.WebClient;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.skcoder.gate_way.Models.Dto;
import com.skcoder.gate_way.Models.User;

import com.skcoder.gate_way.Repo.UserRepository;

import reactor.core.publisher.Mono;

import java.util.Map;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;
    private final WebClient webClient = WebClient.create();
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();
    
    @Value("${user.baseurl}")
    String baseurl;
    
    
    String leaderboardUrl="http://localhost:8011/leaderboard/add";

    public Mono<User> getUserByUsername(String username, String provider) {
        return userRepository.findByUsernameAndProvider(username, provider);
    }

    public Mono<User> getUserByEmail(String email) {
        return userRepository.findByEmail(email);
    }

    public Mono<User> registerOauthUser(String username, String provider, String email) {
        Map<String, String> providerShortcuts = Map.of(
            "GITHUB", "gh",
            "GOOGLE", "google"
        );
        String urlString = baseurl+"/users/add";  
        Dto dto = new Dto(username);

      
        Mono<String> userIdMono = webClient.post()
                .uri(urlString)
                .bodyValue(dto)
                .retrieve()
                .bodyToMono(String.class)
                .onErrorResume(error -> {
                    System.err.println("Failed to create user: " + error.getMessage());
                    return Mono.empty(); 
                });

        String providerSuffix = providerShortcuts.getOrDefault(provider.toUpperCase(), provider.toLowerCase());
        String formattedUsername = username + "-" + providerSuffix;

        return userRepository.findByUsernameAndProvider(formattedUsername, provider)
            .switchIfEmpty(
                userIdMono.flatMap(user_id -> {
                    if (user_id == null || user_id.isEmpty()) {
                        return Mono.error(new RuntimeException("User creation failed, no user_id received"));
                    }
                    User user = new User();
                    user.setUsername(formattedUsername);
                    user.setUserId(user_id);
                    user.setProvider(provider);
                    user.setEmail(email);
                    user.setRole("USER");
//also execute the leaderboard url it takes two param userId and username
//                    webClient.post()
//                    .uri(leaderboardUrl)
//                    .bodyValue(Map.of("userId", user_id, "username", formattedUsername))
//                    .retrieve()
//                    .bodyToMono(Void.class)
//                    .doOnError(error -> System.err.println("Failed to update leaderboard: " + error.getMessage()))
//                    .subscribe(); // Execute without blocking
                    return userRepository.save(user);
                })
            );
    }

}
