package com.algomania.onlinejudge.ProblemHub.DTO;

import lombok.Data;

@Data
public class ExecuteRes {
	
	 boolean accepted;
	 boolean error;
	 String response;
	 String expected;
}
