package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;

import org.springframework.stereotype.Service;

import com.algomania.onlinejudge.ProblemHub.DTO.BoilerplateDTO;
import com.algomania.onlinejudge.ProblemHub.DTO.ProblemDTO;
import com.algomania.onlinejudge.ProblemHub.Entity.Boilerplate;
import com.algomania.onlinejudge.ProblemHub.Entity.Category;
import com.algomania.onlinejudge.ProblemHub.Entity.Difficulty;
import com.algomania.onlinejudge.ProblemHub.Entity.ProblemDetails;
import com.algomania.onlinejudge.ProblemHub.Entity.Problems;
import com.algomania.onlinejudge.ProblemHub.Entity.SampleTestCase;
import com.algomania.onlinejudge.ProblemHub.Entity.Solution;
import com.algomania.onlinejudge.ProblemHub.Exception.CategoryNotFound;
import com.algomania.onlinejudge.ProblemHub.Exception.DifficultyNotFound;

@Service
public class ProblemsHandler {

	BoilerPlateServices boilerPlateServices;
	CategoryServices categoryServices;
	DifficultyServices difficultyServices;
	ProblemDetailsServices problemDetailsServices;
	ProblemsServices problemsServices;
	SolutionsServices solutionsServices;
	SampleTestServices sampleTestServices;
	  public ProblemsHandler(
			  BoilerPlateServices boilerPlateServices,
	            CategoryServices categoryServices,
	            DifficultyServices difficultyServices,
	            ProblemDetailsServices problemDetailsServices,
	            ProblemsServices problemsServices,
	            SolutionsServices solutionsServices,
	            SampleTestServices sampleTestServices) {
	     this.boilerPlateServices=boilerPlateServices;
	        this.categoryServices = categoryServices;
	        this.difficultyServices = difficultyServices;
	        this.problemDetailsServices = problemDetailsServices;
	        this.problemsServices = problemsServices;
	        this.solutionsServices = solutionsServices;
	        this.sampleTestServices=sampleTestServices;
	    }
	  
	  
	  public ProblemDTO AddProblemsdtos(ProblemDTO problemDTO) throws DifficultyNotFound, CategoryNotFound {
		    // Add solution
		    Solution solution = solutionsServices.AddSolution(problemDTO.getSolutionDTO());

		    // Retrieve category and difficulty
		    Optional<Category> category = categoryServices.SearchBytype(problemDTO.getCategoryType());
		    Optional<Difficulty> difficulty = difficultyServices.searchByDifficulty(problemDTO.getLevel());

		    // Validate category and difficulty
		    if (category.isEmpty()) {
		        throw new CategoryNotFound("Category with type " + problemDTO.getCategoryType() + " not found");
		    }

		    if (difficulty.isEmpty()) {
		        throw new DifficultyNotFound("Difficulty level " + problemDTO.getLevel() + " not found");
		    }

		    // Increment category and difficulty counts
		    categoryServices.incrementCategoryCount(category.get().getId());
		    difficultyServices.incrementDifficultyCount(difficulty.get().getId());
List<SampleTestCase>sampleTestCaseslist=new ArrayList<SampleTestCase>();
		    // Add ProblemDetails
		    ProblemDetails problemDetails = problemDetailsServices.addProblem(
		            category.get().getId(),
		            difficulty.get().getId(),
		            problemDTO.getDescription(),
		            problemDTO.getConstrains(),
		            sampleTestCaseslist
		    );

		    // Add SampleTestCases
	sampleTestCaseslist=	  sampleTestServices.saveAllSampleTestCases(problemDTO.getSampleTestDTOs(), problemDetails);
  problemDetailsServices.updateSampleTestCases(problemDetails.getId(), sampleTestCaseslist);
	
		    // Add Boilerplates
		    for (BoilerplateDTO it : problemDTO.getBoilerplateDTOs()) {
		        boilerPlateServices.AddBoilerplate(problemDetails.getId(), it.getLang_type(), it.getBoilerplateCode());
		    }

		    // Add Problems
		    Problems pr = problemsServices.addProblem(
		            problemDetails.getId(),
		            difficulty.get().getId(),
		            category.get().getId(),
		            problemDTO.getTitle()
		    );

		    return problemDTO;
		}

	
}
