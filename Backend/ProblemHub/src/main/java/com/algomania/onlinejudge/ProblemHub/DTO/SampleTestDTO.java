package com.algomania.onlinejudge.ProblemHub.DTO;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data

@AllArgsConstructor
@Builder
public class SampleTestDTO {
	private  String input;
	   
	
	  private String output;
	   
	   
	  private String explanation;
}
