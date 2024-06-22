package com.algomania.onlinejudge.ProblemHub.Controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algomania.onlinejudge.ProblemHub.Entity.ProblemDetails;
import com.algomania.onlinejudge.ProblemHub.Services.ProblemDetailsServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/Algomania/ProblemDetails")
public class ProblemDetailsController {

    ProblemDetailsServices problemDetailsServices;

    public ProblemDetailsController(ProblemDetailsServices problemDetailsServices) {
        this.problemDetailsServices = problemDetailsServices;
    }

    @Operation(summary = "Get problem details by ID", description = "Fetches problem details by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Problem details found", 
                     content = @Content(mediaType = "application/json", 
                                        schema = @Schema(implementation = ProblemDetails.class))),
        @ApiResponse(responseCode = "404", description = "Problem details not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<Object> searchProblems(
        @Parameter(description = "ID of the problem details", required = true)
        @PathVariable int id) {
        
        Optional<ProblemDetails> optionalProblem = problemDetailsServices.searchByproblemDetailsbyId(id);
        if (optionalProblem.isPresent()) {
            return ResponseEntity.ok(optionalProblem.get());
        } else {
            return ResponseEntity.status(404).body("Problem details not found");
        }
    }
}
