package com.algomania.onlinejudge.ProblemHub.Controller;

import com.algomania.onlinejudge.ProblemHub.Entity.Category;
import com.algomania.onlinejudge.ProblemHub.Services.CategoryServices;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.media.Content;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.responses.ApiResponse;
import io.swagger.v3.oas.annotations.responses.ApiResponses;

@RestController
@RequestMapping("Algomania/Category")
public class CategoryController {

    @Autowired
    private CategoryServices categoryServices;

//    @Operation(summary = "Increment category count", description = "Increments the count of a category by its ID")
//    @ApiResponses(value = {
//        @ApiResponse(responseCode = "204", description = "No Content - Successfully incremented the category count"),
//        @ApiResponse(responseCode = "404", description = "Category not found"),
//        @ApiResponse(responseCode = "500", description = "Internal server error")
//    })
//    @PostMapping("/increment-category-count/{id}")
//    public ResponseEntity<Void> incrementCategoryCount(
//        @Parameter(description = "ID of the category to increment", required = true)
//        @PathVariable int id) {
//        categoryServices.incrementCategoryCount(id);
//        return ResponseEntity.noContent().build();
//    }

    @Operation(summary = "Get all categories", description = "Fetches all the categories")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation",
                     content = @Content(mediaType = "application/json",
                                        schema = @Schema(implementation = Category.class))),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/allcategories")
    public ResponseEntity<List<Category>> getAllCategories() {
        List<Category> categories = categoryServices.getAllCategories();
        return new ResponseEntity<>(categories, HttpStatus.OK);
    }

    @Operation(summary = "Get category count by ID", description = "Fetches the count of a category by its ID")
    @ApiResponses(value = {
        @ApiResponse(responseCode = "200", description = "Successful operation"),
        @ApiResponse(responseCode = "404", description = "Category not found"),
        @ApiResponse(responseCode = "500", description = "Internal server error")
    })
    @GetMapping("/count/{id}")
    public ResponseEntity<Integer> getcount(
        @Parameter(description = "ID of the category to get the count", required = true)
        @RequestParam int id) {
        int count = categoryServices.getcountById(id);
        return new ResponseEntity<>(count, HttpStatus.OK);
    }
}
