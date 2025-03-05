package com.skcoder.gate_way.Repo;

import org.springframework.data.repository.reactive.ReactiveCrudRepository;
import reactor.core.publisher.Mono;
import com.skcoder.gate_way.Models.User;

public interface UserRepository extends ReactiveCrudRepository<User, String> { // Change Long to String
    Mono<User> findByUsername(String username);
    Mono<User> findByUsernameAndProvider(String username, String provider);
    Mono<User> findByEmail(String email);
}
