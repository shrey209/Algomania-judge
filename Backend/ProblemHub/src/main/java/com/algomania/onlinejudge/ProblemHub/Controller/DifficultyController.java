package com.algomania.onlinejudge.ProblemHub.Controller;

import java.util.List;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algomania.onlinejudge.ProblemHub.Entity.Difficulty;
import com.algomania.onlinejudge.ProblemHub.Services.DifficultyServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("Algomania/Difficulty")
public class DifficultyController {

    DifficultyServices difficultyServices;

    public DifficultyController(DifficultyServices difficultyServices) {
        this.difficultyServices = difficultyServices;
    }

    
  //@PostMapping("/increment-difficulty-count/{id}")
  //public void incrementDifficultyCount(@PathVariable int id) {
//      difficultyServices.incrementDifficultyCount(id);
  //}
    
    @Operation(summary = "Get all difficulties", description = "Fetches all the difficulty levels")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = Difficulty.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/alldifficulty")
    public ResponseEntity<List<Difficulty>> getAllDifficulties() {
        List<Difficulty> difficulties = difficultyServices.getallDifficulties();
        return new ResponseEntity<>(difficulties, HttpStatus.OK);
    }

    @Operation(summary = "Get difficulty count by ID", description = "Fetches the count of a difficulty by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Difficulty not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> getcount(
        @Parameter(description = "ID of the difficulty to get the count", required = true)
        @RequestParam int id) {
        int count = difficultyServices.getDifficultyCount(id);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
