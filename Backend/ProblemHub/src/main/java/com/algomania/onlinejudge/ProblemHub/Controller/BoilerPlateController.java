package com.algomania.onlinejudge.ProblemHub.Controller;

import java.util.Optional;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import com.algomania.onlinejudge.ProblemHub.DTO.BoilerplateDTO;

import com.algomania.onlinejudge.ProblemHub.Entity.Boilerplate;
import com.algomania.onlinejudge.ProblemHub.Services.BoilerPlateServices;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/Algomania/BoilerPlate")
public class BoilerPlateController {
	
	BoilerPlateServices boilerPlateServices;
	
	public BoilerPlateController(BoilerPlateServices boilerPlateServices) {
		this.boilerPlateServices=boilerPlateServices;
	}
	 @Operation(summary = "Get Boilerplate by Problem Details ID and Language Type",
             description = "Fetches the boilerplate code for a specific problem details ID and language type.")
  @ApiResponses(value = {
      @ApiResponse(responseCode = "200", description = "Successful operation", 
                   content = @Content(mediaType = "application/json", 
                                      schema = @Schema(implementation = Boilerplate.class))),
      @ApiResponse(responseCode = "404", description = "Boilerplate not found"),
      @ApiResponse(responseCode = "500", description = "Internal server error")
  })
	 @GetMapping("/get")
	    public ResponseEntity<Object> getBoilerplate(
	            @RequestParam int problemDetailsId,
	            @RequestParam String lang_type) {
	        
	        Optional<Boilerplate> boilerplate = boilerPlateServices.findByProblemDetailsIdAndLanguageType(
	                problemDetailsId, lang_type);

	        if (boilerplate.isPresent()) {
	            return ResponseEntity.ok(boilerplate.get());
	        } else {
	            return ResponseEntity.status(404).body("Boilerplate not found with problem details id:-"+problemDetailsId+"lang type"+lang_type);
	        }
	    }
	    }


