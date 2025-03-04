package com.skcoder.gate_way.Services;


import org.springframework.beans.factory.annotation.Autowired;

import org.springframework.stereotype.Service;
import org.springframework.security.crypto.bcrypt.BCryptPasswordEncoder;

import com.fasterxml.jackson.databind.introspect.AccessorNamingStrategy.Provider;
import com.skcoder.gate_way.Models.Role;
import com.skcoder.gate_way.Models.User;
import com.skcoder.gate_way.Repo.UserRepository;

import java.util.HashSet;
import java.util.Map;
import java.util.Optional;
import java.util.Set;

@Service
public class AuthService {
    @Autowired
    private UserRepository userRepository;

    @Autowired
    private CaffeineCacheService cacheService;
    
    private final BCryptPasswordEncoder passwordEncoder = new BCryptPasswordEncoder();

  
    public String register(String username, String password,String email) {
        if (userRepository.findByUsername(username).isPresent()) {
            return "Username already taken!";
        }
       
    String otp=    cacheService.generateAndStoreOTP(email);
        
Set<Role>roles=new HashSet<>();
        roles.add(Role.USER);
        User user = new User();
        user.setUsername(username);
        user.setPassword(passwordEncoder.encode(password)); 
        user.setRoles(roles);
        user.setPending(true);
        user.setProvider("LOCAL");
        user.setEmail(email);
       
        User user1= userRepository.save(user);
       System.out.println(user1.toString());
       
        return otp;
    }

 
    public User registerOauthUser(String username, String provider) {
        Map<String, String> providerShortcuts = Map.of(
            "GITHUB", "gh",
            "GOOGLE", "google"
         
        );

        String providerSuffix = providerShortcuts.getOrDefault(provider.toUpperCase(), provider.toLowerCase());
        String formattedUsername = username + "-" + providerSuffix;

        Optional<User> appUser = userRepository.findByUsernameAndProvider(formattedUsername, provider);
        if (appUser.isPresent()) return appUser.get();

        Set<Role> roles = new HashSet<>();
        roles.add(Role.USER);

        User user = new User();
        user.setUsername(formattedUsername);
        user.setRoles(roles);
        user.setProvider(provider);
        userRepository.save(user);
        return user;
    }

    
    public String login(String username, String password) {
        Optional<User> userOpt = userRepository.findByUsername(username);
        if (userOpt.isEmpty()) {
            return "Invalid credentials!";
        }

        User user = userOpt.get();
        if (!passwordEncoder.matches(password, user.getPassword())) {
            return "Invalid credentials!";
        }

        return JwtUtil.generateToken(user.getUsername(), user.getRoles(),user.getId());
    }
    
    
    
    public String verifyOtp(String gmail, String otp) throws Exception {
        String cacheOtp = cacheService.get(gmail);
        System.out.println(cacheOtp+"cahced");
        if (cacheOtp == null || !cacheOtp.equals(otp)) {
            throw new Exception("Invalid OTP or the OTP may have expired.");
        }
        
        User user = userRepository.findByEmail(gmail)
                .orElseThrow(() -> new Exception("User not found for email: " + gmail));

        return JwtUtil.generateToken(user.getUsername(), user.getRoles(), user.getId());
    }


}
