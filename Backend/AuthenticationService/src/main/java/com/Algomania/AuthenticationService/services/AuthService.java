package com.Algomania.AuthenticationService.services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;

import com.Algomania.AuthenticationService.Modals.AuthRequest;
import com.Algomania.AuthenticationService.Repository.AlgoRepo;
import com.Algomania.AuthenticationService.config.AlgoUser;



@Service
public class AuthService {

    @Autowired
    private AlgoRepo repository;
    @Autowired
    private PasswordEncoder passwordEncoder;

    @Autowired
    private JwtService jwtService;

    public String saveUser( AuthRequest authRequest) {
        authRequest.setPassword(passwordEncoder.encode(authRequest.getPassword()));
        repository.save(new AlgoUser(0,  authRequest.getUserid(), authRequest.getUsername(), authRequest.getPassword()));
        return "user added to the system";
    }

    public String generateToken(String username) {
        return jwtService.generateToken(username);
    }

    public void validateToken(String token) {
        jwtService.validateToken(token);
    }

    public String findusername(String username) {
 Optional<AlgoUser> user=   repository.findByUsername(username);
 if(user.isPresent()) {
	 return  user.get().getUsername();
 }
 else {
	 throw new UsernameNotFoundException("invalid username");
 }
    }

}
