package com.algomania.onlinejudge.ProblemHub.Controller;

import java.io.IOException;
import java.util.Base64;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.multipart.MultipartFile;

import com.algomania.onlinejudge.ProblemHub.Entity.Filedb;
import com.algomania.onlinejudge.ProblemHub.Services.FileStroageService;

import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("/Algomania/files")
public class FiledbController {

    FileStroageService fileStroageService;

    public FiledbController(FileStroageService fileStroageService) {
        this.fileStroageService = fileStroageService;
    }

    @Operation(summary = "Upload files", description = "Uploads input and output files for a problem")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Files uploaded successfully", 
                     content = @Content(mediaType = "application/json", 
                                        schema = @Schema(implementation = Filedb.class))),
        @ApiResponse(responseCode = "500", description = "Failed to upload files")
    })
    @PostMapping("/upload")
    public ResponseEntity<?> uploadFiles(
        @Parameter(description = "Input file to upload", required = true)
        @RequestParam("inputFile") MultipartFile inputFile,

        @Parameter(description = "Output file to upload", required = true)
        @RequestParam("outputFile") MultipartFile outputFile,

        @Parameter(description = "ID of the problem details", required = true)
        @RequestParam("problemid") int problemdetailsid) {
        
        try {
            Filedb uploadedFiles = fileStroageService.store(problemdetailsid, inputFile, outputFile);
            return ResponseEntity.ok(uploadedFiles);
        } catch (IOException e) {
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to upload files");
        }
    }

    @Operation(summary = "Get file data by ID", description = "Fetches file data by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File found",
                     content = @Content(mediaType = "application/json", 
                                        schema = @Schema(implementation = Filedb.class))),
        @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping("/{id}")
    public ResponseEntity<?> getFileDataById(
        @Parameter(description = "ID of the file", required = true)
        @PathVariable int id) {
        
        java.util.Optional<Filedb> fileOptional = fileStroageService.getFileById(id);
        if (fileOptional.isPresent()) {
            Filedb file = fileOptional.get();
            return ResponseEntity.ok(file);
        } else {
            return ResponseEntity.notFound().build();
        }
    }

    @Operation(summary = "Get file by problem ID", description = "Fetches file by problem ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "File found",
                     content = @Content(mediaType = "application/json", 
                                        schema = @Schema(implementation = Filedb.class))),
        @ApiResponse(responseCode = "404", description = "File not found")
    })
    @GetMapping("/problem/{problemId}")
    public ResponseEntity<?> getFileByProblemId(
        @Parameter(description = "ID of the problem", required = true)
        @PathVariable int problemId) {
        
        java.util.Optional<Filedb> fileOptional = fileStroageService.getfilebyproblemids(problemId);
        return fileOptional.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get input file by problem details ID", description = "Fetches input file data by problem details ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Input file found"),
        @ApiResponse(responseCode = "404", description = "Input file not found")
    })
    @GetMapping("/problem/input/{problemDetailsId}")
    public ResponseEntity<byte[]> getInputFileByProblemDetailsId(
        @Parameter(description = "ID of the problem details", required = true)
        @PathVariable int problemDetailsId) {
        
        java.util.Optional<byte[]> inputFileData = fileStroageService.getInputFileDataByProblemDetailsId(problemDetailsId);
        return inputFileData.map(data -> ResponseEntity.ok().body(data))
                            .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @Operation(summary = "Get output file by problem details ID", description = "Fetches output file data by problem details ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Output file found"),
        @ApiResponse(responseCode = "404", description = "Output file not found")
    })
    @GetMapping("/problem/output/{problemDetailsId}")
    public ResponseEntity<byte[]> getOutputFileByProblemDetailsId(
        @Parameter(description = "ID of the problem details", required = true)
        @PathVariable int problemDetailsId) {
        
        java.util.Optional<byte[]> outputFileData = fileStroageService.getOutputFileDataByProblemDetailsId(problemDetailsId);
        return outputFileData.map(data -> ResponseEntity.ok().body(data))
                             .orElseGet(() -> ResponseEntity.notFound().build());
    }
}
