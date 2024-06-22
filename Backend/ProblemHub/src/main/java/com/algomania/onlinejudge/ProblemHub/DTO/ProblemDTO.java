package com.algomania.onlinejudge.ProblemHub.DTO;

import java.util.ArrayList;

import com.algomania.onlinejudge.ProblemHub.Entity.SampleTestCase;
import com.algomania.onlinejudge.ProblemHub.Entity.Solution;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;

@Data
@AllArgsConstructor
@Builder
public class ProblemDTO {

	String title;
	
	String level;
	
	String categoryType;
	
	String description;
	
	String constrains;
	
	SolutionDTO solutionDTO;
	
	ArrayList<BoilerplateDTO>boilerplateDTOs;
	
	ArrayList<SampleTestDTO>sampleTestDTOs;
	
}
