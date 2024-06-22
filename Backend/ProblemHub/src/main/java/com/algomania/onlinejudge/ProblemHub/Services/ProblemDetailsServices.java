package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algomania.onlinejudge.ProblemHub.Entity.ProblemDetails;
import com.algomania.onlinejudge.ProblemHub.Entity.SampleTestCase;
import com.algomania.onlinejudge.ProblemHub.Repository.ProblemDetailsRepository;

@Service
public class ProblemDetailsServices {

	ProblemDetailsRepository problemDetailsRepository;
	
	ProblemDetailsServices(ProblemDetailsRepository problemDetailsRepository){
		this.problemDetailsRepository=problemDetailsRepository;
	}
	
	//add problem details
	public ProblemDetails addProblem(int categoryId, int solutionId, String description, String constraints, List<SampleTestCase> sampleTestCases) {
        ProblemDetails problemDetails = new ProblemDetails();
        problemDetails.setCategoryId(categoryId);
        problemDetails.setSolutionId(solutionId);
        problemDetails.setDescription(description);
        problemDetails.setConstraints(constraints);
        problemDetails.setSampleTestCases(sampleTestCases);
        return problemDetailsRepository.save(problemDetails);
    }
	
 public Optional<ProblemDetails> searchByproblemDetailsbyId(int id) {
	 return problemDetailsRepository.findById(id);
 }

 public ProblemDetails updateSampleTestCases(int problemDetailsId, List<SampleTestCase> newSampleTestCases) {
     Optional<ProblemDetails> optionalProblemDetails = problemDetailsRepository.findById(problemDetailsId);
     
     if (optionalProblemDetails.isPresent()) {
         ProblemDetails problemDetails = optionalProblemDetails.get();
         problemDetails.setSampleTestCases(newSampleTestCases);
         return problemDetailsRepository.save(problemDetails);
     } else {
         throw new RuntimeException("ProblemDetails with id " + problemDetailsId + " not found");
     }
 }

}
