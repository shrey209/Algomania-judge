package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algomania.onlinejudge.ProblemHub.DTO.SolutionDTO;
import com.algomania.onlinejudge.ProblemHub.Entity.Solution;
import com.algomania.onlinejudge.ProblemHub.Repository.solutionsRepository;

import jakarta.persistence.Lob;

@Service
public class SolutionsServices {

	solutionsRepository solutionsRepository;
	
	SolutionsServices(solutionsRepository solutionsRepository){
		this.solutionsRepository=solutionsRepository;
	}
	
	 @Lob
	    private String overview;
	    
	    @Lob
	    private String algorithm;
	    
	    @Lob
	    private String code;
	    
	    @Lob
	    private String Intuition;
	//Add solution
public Solution	AddSolution(SolutionDTO solution) {
	return solutionsRepository.save(new Solution(0,solution.getOverview(),solution.getIntuition(),solution.getCode(),solution.getAlgorithm()));
}

public Optional<Solution> searchSolutionById(int id) {
	return solutionsRepository.findById(id);
}
	
}