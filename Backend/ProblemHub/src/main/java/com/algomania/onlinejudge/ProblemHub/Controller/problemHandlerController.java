package com.algomania.onlinejudge.ProblemHub.Controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import com.algomania.onlinejudge.ProblemHub.DTO.ProblemDTO;
import com.algomania.onlinejudge.ProblemHub.Exception.CategoryNotFound;
import com.algomania.onlinejudge.ProblemHub.Exception.DifficultyNotFound;
import com.algomania.onlinejudge.ProblemHub.Services.ProblemsHandler;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/Algomania/problemHandler")
public class problemHandlerController {

    ProblemsHandler problemsHandler;

    public problemHandlerController(ProblemsHandler problemsHandler) {
        this.problemsHandler = problemsHandler;
    }

    @Operation(summary = "Add a problem", description = "Adds a new problem to the database")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Problem added successfully", 
                     content = @Content(mediaType = "application/json", 
                                        schema = @Schema(implementation = ProblemDTO.class))),
        @ApiResponse(responseCode = "404", description = "Category or Difficulty not found", 
                     content = @Content(mediaType = "application/json")),
        @ApiResponse(responseCode = "500", description = "Internal server error", 
                     content = @Content(mediaType = "application/json"))
    })
    @PostMapping("/add")
    public ResponseEntity<?> addProblemToDB(
        @Parameter(description = "Problem data to add", required = true)
        @RequestBody ProblemDTO problemDTO) {
        
        try {
            problemsHandler.AddProblemsdtos(problemDTO);
            return ResponseEntity.ok(problemDTO);
        } catch (CategoryNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Category not found: " + e.getMessage());
        } catch (DifficultyNotFound e) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body("Difficulty not found: " + e.getMessage());
        } catch (Exception e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("An error occurred while adding problem: " + e.getMessage());
        }
    }
}
