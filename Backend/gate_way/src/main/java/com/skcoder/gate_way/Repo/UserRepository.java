package com.skcoder.gate_way.Repo;


import org.springframework.data.jpa.repository.JpaRepository;

import com.skcoder.gate_way.Models.User;

import java.util.Optional;

public interface UserRepository extends JpaRepository<User, Long> {
    Optional<User> findByUsername(String username);
    Optional<User> findByUsernameAndProvider(String username, String provider); 
    Optional<User>findByEmail(String email);
}
