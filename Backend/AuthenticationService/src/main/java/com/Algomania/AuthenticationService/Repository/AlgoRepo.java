package com.Algomania.AuthenticationService.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;

import com.Algomania.AuthenticationService.config.AlgoUser;



public interface AlgoRepo extends JpaRepository<AlgoUser, Integer> {
Optional<AlgoUser>findByUsername(String username);
}
