package com.Algomania.users_microservice.Controller;

import com.Algomania.users_microservice.Entity.User;
import com.Algomania.users_microservice.services.UserService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.server.ServerHttpRequest;
import org.springframework.web.server.ServerWebExchange;

import org.springframework.web.bind.annotation.*;
import org.springframework.web.server.ResponseStatusException;

import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.Collections;


@RestController
@RequestMapping("/users")
public class UserController {

    private final UserService userService;

    @Autowired
    public UserController(UserService userService) {
        this.userService = userService;
    }

    @GetMapping("/getuser")
    public Mono<User> getUserById(@RequestHeader(value = "X-User-ID", required = false) String userId) {
    	System.out.println("req was sent");
        if (userId == null) {
            return Mono.error(new ResponseStatusException(HttpStatus.UNAUTHORIZED, "User ID not found in headers"));
        }
        System.out.println(userId);
        return userService.getUserById(userId);
    }

    @Operation(summary = "Add New User", description = "Create a new user with the provided data.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "201", description = "User successfully created")
    })
    @PostMapping("/add")
    public Mono<String> saveUser(@RequestBody dto dtos) {
    	System.out.println("hit");
        User user = User.builder()
                .name(dtos.getName())
                .mediumCount(0)
                .hardCount(0)
                .easyCount(0)
                .solvedProblemsId(Collections.emptySet())
                .todoproblems(Collections.emptySet())
                .build();
        
        return userService.saveUser(user)
                .map(savedUser -> savedUser.getId()); 
    }

    @Operation(summary = "Increment Easy Problem Count", description = "Increment the count of easy problems solved by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully incremented the easy problem count")
    })
    @PutMapping("/{userId}/incrementEasyCount")
    public Mono<User> incrementEasyCount(@PathVariable String userId) {
        return userService.incrementEasyCount(userId);
    }

    @Operation(summary = "Increment Medium Problem Count", description = "Increment the count of medium problems solved by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully incremented the medium problem count")
    })
    @PutMapping("/{userId}/incrementMediumCount")
    public Mono<User> incrementMediumCount(@PathVariable String userId) {
        return userService.incrementMediumCount(userId);
    }

    @Operation(summary = "Increment Hard Problem Count", description = "Increment the count of hard problems solved by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully incremented the hard problem count")
    })
    @PutMapping("/{userId}/incrementHardCount")
    public Mono<User> incrementHardCount(@PathVariable String userId) {
        return userService.incrementHardCount(userId);
    }

    @Operation(summary = "Add Solved Problem ID", description = "Add a solved problem ID to the user's list of solved problems.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully added the solved problem ID")
    })
    @PutMapping("/{userId}/addSolvedProblem/{problemId}")
    public Mono<User> addSolvedProblemId(@PathVariable String userId, @PathVariable int problemId) {
        return userService.addSolvedProblemId(userId, problemId);
    }

    @Operation(summary = "Check If Problem Solved", description = "Check if a specific problem has been solved by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully checked if the problem is solved")
    })
    @GetMapping("/{userId}/has/{problemId}/solved")
    public Mono<Boolean> checkIfProblemSolved(
            @PathVariable String userId,
            @PathVariable int problemId
    ) {
        return userService.checkIfProblemSolved(userId, problemId);
    }

    @Operation(summary = "Get All Problem IDs by User ID", description = "Retrieve all problem IDs solved by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved all problem IDs")
    })
    @GetMapping("/{userId}/problems")
    public Flux<Integer> getAllProblemIdsByUserId(@PathVariable String userId) {
        return userService.getAllProblemIdsByUserId(userId);
    }

    @Operation(summary = "Get Subset of Solved Problem IDs", description = "Retrieve a subset of solved problem IDs by the user.")
    @ApiResponses(value = {
            @ApiResponse(responseCode = "200", description = "Successfully retrieved the subset of solved problem IDs")
    })
    @GetMapping("/{userId}/problems/{start}/{end}")
    public Flux<Integer> getSubsetOfSolvedProblemIds(
            @PathVariable String userId,
            @PathVariable int start,
            @PathVariable int end) {
        return userService.getSubsetOfSolvedProblemIds(userId, start, end);
    }
}
