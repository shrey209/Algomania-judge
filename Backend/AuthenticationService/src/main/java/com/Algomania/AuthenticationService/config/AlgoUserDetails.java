package com.Algomania.AuthenticationService.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.stereotype.Component;

import com.Algomania.AuthenticationService.Repository.AlgoRepo;

import java.util.Optional;

@Component
public class AlgoUserDetails implements UserDetailsService {

    @Autowired
    private AlgoRepo customRepo;

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        Optional<AlgoUser> credential = customRepo.findByUsername(username);
      if(credential.isPresent()) {
    	  return (UserDetails) credential.get();
      }
      else {
    	  throw new UsernameNotFoundException("invalid user "+username);
      }
    }
}
