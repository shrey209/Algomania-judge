package com.algomania.onlinejudge.ProblemHub.Controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algomania.onlinejudge.ProblemHub.Entity.Solution;
import com.algomania.onlinejudge.ProblemHub.Services.SolutionsServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/Algomania/Solution")
public class SolutionController {

    SolutionsServices solutionsServices;
    
    public SolutionController(SolutionsServices solutionsServices){
        this.solutionsServices=solutionsServices;
    }
    
    @Operation(summary = "Get solution by ID", description = "Fetches solution details by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Solution found", 
                     content = @Content(mediaType = "application/json", 
                                        schema = @Schema(implementation = Solution.class))),
        @ApiResponse(responseCode = "404", description = "Solution not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> searchProblems(
        @Parameter(description = "ID of the solution", required = true)
        @PathVariable int id) {
        
        Optional<Solution> solutionOptional = solutionsServices.searchSolutionById(id);
        if (solutionOptional.isPresent()) {
            return ResponseEntity.ok(solutionOptional.get());
        } else {
            return ResponseEntity.status(404).body("Solution not found");
        }
    }
}
