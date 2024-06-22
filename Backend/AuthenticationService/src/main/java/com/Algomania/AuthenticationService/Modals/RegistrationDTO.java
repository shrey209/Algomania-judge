package com.Algomania.AuthenticationService.Modals;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@AllArgsConstructor
@NoArgsConstructor
@Data
@Builder
public class RegistrationDTO {

	String username;
	
	String firstname;
	
	String lastname;
	
	String password;
	
	
	
}
