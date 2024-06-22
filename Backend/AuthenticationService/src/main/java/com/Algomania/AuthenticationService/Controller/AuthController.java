package com.Algomania.AuthenticationService.Controller;


import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.ResponseEntity;
import org.springframework.security.authentication.AuthenticationManager;
import org.springframework.security.authentication.UsernamePasswordAuthenticationToken;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.client.RestTemplate;
import org.springframework.web.util.UriComponentsBuilder;

import com.Algomania.AuthenticationService.Modals.AuthRequest;
import com.Algomania.AuthenticationService.Modals.LoginDTO;
import com.Algomania.AuthenticationService.Modals.LoginResponseDTO;
import com.Algomania.AuthenticationService.Modals.RegistrationDTO;
import com.Algomania.AuthenticationService.Modals.Userdto;
import com.Algomania.AuthenticationService.Repository.AlgoRepo;
import com.Algomania.AuthenticationService.config.AlgoUser;
import com.Algomania.AuthenticationService.services.AuthService;
import com.fasterxml.jackson.databind.JsonNode;
import com.fasterxml.jackson.databind.ObjectMapper;

@RestController
@RequestMapping("/auth")
public class AuthController {
    @Autowired
    private AuthService service;

    @Autowired
    private AuthenticationManager authenticationManager;
    
    @Autowired
    RestTemplate restTemplate;
    @Autowired
    private ObjectMapper objectMapper;
    
    @Autowired
    AlgoRepo algoRepo;

    @PostMapping("/register")
    public LoginResponseDTO addNewUser(@RequestBody RegistrationDTO registrationDTO) {
        Userdto userdto = new Userdto(registrationDTO.getFirstname(), registrationDTO.getLastname());
        String addUrl = "http://localhost:8010/users/add";
        String leaderboardUrl = "http://localhost:8011/leaderboard/add";

        try {
            ResponseEntity<String> responseEntity = restTemplate.postForEntity(addUrl, userdto, String.class);

            if (responseEntity.getStatusCode().is2xxSuccessful()) {
                String responseBody = responseEntity.getBody();
                JsonNode jsonNode = objectMapper.readTree(responseBody);
                String id = jsonNode.get("id").asText();
                AuthRequest authRequest = new AuthRequest(id, registrationDTO.getUsername(), registrationDTO.getPassword());

                // Save user details
                service.saveUser(authRequest);

                // Call the leaderboard URL with userId as request parameter
                UriComponentsBuilder uriBuilder = UriComponentsBuilder.fromHttpUrl(leaderboardUrl)
                        .queryParam("userId", id);

                ResponseEntity<String> leaderboardResponse = restTemplate.postForEntity(uriBuilder.toUriString(), null, String.class);

                if (leaderboardResponse.getStatusCode().is2xxSuccessful()) {
                    return new LoginResponseDTO(id, service.generateToken(registrationDTO.getUsername()));
                } else {
                    // Handle leaderboard call failure
                    throw new RuntimeException("Failed to add user to leaderboard");
                }
            } else {
                // Handle user creation failure
                throw new RuntimeException("Failed to create user");
            }
        } catch (Exception e) {
            // Log the error and rethrow as runtime exception
            e.printStackTrace();
            throw new RuntimeException("Error while adding new user", e);
        }
    }


    @PostMapping("/token")
    @CrossOrigin(origins = "http://localhost:3000")
    public LoginResponseDTO getToken(@RequestBody LoginDTO loginDTO) {
        Authentication authenticate = authenticationManager.authenticate(new UsernamePasswordAuthenticationToken(loginDTO.getUsername(), loginDTO.getPassword()));
        if (authenticate.isAuthenticated()) {
         
        	LoginResponseDTO loginResponseDTO=new LoginResponseDTO();
       Optional<AlgoUser> algoUser =	algoRepo.findByUsername(loginDTO.getUsername());
       
       loginResponseDTO.setUserid(algoUser.get().getUserid());
       loginResponseDTO.setJwt(service.generateToken(loginDTO.getUsername()));
            return  loginResponseDTO;
        } else {
            throw new RuntimeException("invalid access");
        }
    }

    @GetMapping("/validate")
    public String validateToken(@RequestParam("token") String token) {
        service.validateToken(token);
        return "Token is valid";
    }
    
    @GetMapping("/finduser")
    public String findUsers(@RequestParam("username") String username) {
        return service.findusername(username);
    }

}
    
