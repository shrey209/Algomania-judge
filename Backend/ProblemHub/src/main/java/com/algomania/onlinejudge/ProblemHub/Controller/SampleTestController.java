package com.algomania.onlinejudge.ProblemHub.Controller;


import java.util.List;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algomania.onlinejudge.ProblemHub.DTO.SampleTestDTO;
import com.algomania.onlinejudge.ProblemHub.Entity.SampleTestCase;
import com.algomania.onlinejudge.ProblemHub.Services.SampleTestServices;

@RestController
@RequestMapping("Algomania/SampleTestCase")
public class SampleTestController {

	
	   private final SampleTestServices sampleTestService;

	
	    public SampleTestController(SampleTestServices sampleTestService) {
	        this.sampleTestService = sampleTestService;
	    }

	    @GetMapping("/{problemDetailsId}")
	    public ResponseEntity<List<SampleTestDTO>> getSampleTestCasesByProblemDetailsId(@PathVariable int problemDetailsId) {
	        List<SampleTestDTO> sampleTestCases = sampleTestService.getSampleTestCasesByProblemDetailsId(problemDetailsId);
	        return ResponseEntity.ok(sampleTestCases);
	    }
	
}
