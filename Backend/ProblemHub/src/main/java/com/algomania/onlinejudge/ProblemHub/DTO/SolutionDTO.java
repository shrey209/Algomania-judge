package com.algomania.onlinejudge.ProblemHub.DTO;

import jakarta.persistence.Column;
import jakarta.persistence.Lob;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@Builder
public class SolutionDTO {
	String overview;
	    
	 String algorithm;
	    
	    String code;
	    
	     String Intuition;
}
