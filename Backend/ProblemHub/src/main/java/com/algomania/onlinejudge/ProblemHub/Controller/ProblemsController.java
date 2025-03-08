package com.algomania.onlinejudge.ProblemHub.Controller;

import java.util.List;
import java.util.Optional;

import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestHeader;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algomania.onlinejudge.ProblemHub.DTO.ProblemsFilterDTO;
import com.algomania.onlinejudge.ProblemHub.Entity.Problems;
import com.algomania.onlinejudge.ProblemHub.Services.ProblemsServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import jakarta.ws.rs.GET;

@RestController
@RequestMapping("Algomania/problem")
public class ProblemsController {

    private final ProblemsServices problemsServices;

    public ProblemsController(ProblemsServices problemsServices) {
        this.problemsServices = problemsServices;
    }

    @Operation(summary = "Get problem by ID", description = "Fetches problem details by its ID")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Problem found",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Problems.class))),
            @ApiResponse(responseCode = "404", description = "Problem not found")
    })
    @GetMapping("/get/{id}")
    public ResponseEntity<Object> searchProblems(
            @Parameter(description = "ID of the problem", required = true)
            @PathVariable int id) {

        Optional<Problems> optionalProblem = problemsServices.searchProblemsById(id);
        if (optionalProblem.isPresent()) {
            return ResponseEntity.ok(optionalProblem.get());
        } else {
            return ResponseEntity.status(404).body("Problem not found");
        }
    }

    @Operation(summary = "Get problems paginated", description = "Fetches a paginated list of problems (default size 8)")
    @GetMapping("/page")
    public ResponseEntity<Page<Problems>> getProblemsPaginated(
            @RequestParam(name = "page", defaultValue = "1") int page) {

        Page<Problems> problemsPage = problemsServices.getProblemsPaginated(page); // Default size is 8
        return ResponseEntity.ok(problemsPage);
    }


    @Operation(summary = "Get problems by lists of difficulty and category IDs, excluding specific IDs", description = "Fetches problems by lists of difficulty and category IDs, excluding specific problem IDs")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successful operation",
                    content = @Content(mediaType = "application/json",
                            schema = @Schema(implementation = Page.class))),
            @ApiResponse(responseCode = "400", description = "Invalid request"),
            @ApiResponse(responseCode = "404", description = "Not found")
    })
    @PostMapping("/filter")
    public ResponseEntity<Page<Problems>> getProblemsByCategoryAndDifficultyExcludingIdsPost(
            @RequestBody ProblemsFilterDTO filterDTO,@RequestHeader(value = "X-User-ID", required = false) String userid) {
        System.out.println(filterDTO);
        Page<Problems> problemsPage = problemsServices.findProblemsByDifficultyAndCategoryExcludingIds(
                filterDTO.getDifficultyIds(), filterDTO.getCategoryIds(), filterDTO.isSolved(),userid, filterDTO.getPage());
        
       
            return ResponseEntity.ok(problemsPage);
     
    }
    @GetMapping("/getByList")
    public ResponseEntity<Page<Problems>> getProblemsByListOfIds(
            @RequestParam List<Integer> problemIds,
            @RequestParam(defaultValue = "1") int page) {

        Page<Problems> problemsPage = problemsServices.getproblemsbylistofid(problemIds, page);

        return ResponseEntity.ok(problemsPage);
    }
    
    @GetMapping("/searchByTitle")
    public List<Problems> searchByTitle(@RequestParam String title) {
        return problemsServices.searchByTitle(title);
    }
}
