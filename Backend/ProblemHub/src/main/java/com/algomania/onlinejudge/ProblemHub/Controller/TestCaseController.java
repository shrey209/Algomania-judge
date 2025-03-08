package com.algomania.onlinejudge.ProblemHub.Controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.algomania.onlinejudge.ProblemHub.Entity.TestCase;
import com.algomania.onlinejudge.ProblemHub.Services.TestCaseService;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/testcases")
public class TestCaseController {

    private final TestCaseService testCaseService;

    public TestCaseController(TestCaseService testCaseService) {
        this.testCaseService = testCaseService;
    }

    @PostMapping
    public ResponseEntity<TestCase> addTestCase(@RequestBody TestCase testCase) {
        return ResponseEntity.ok(testCaseService.addTestCase(testCase));
    }

    @GetMapping("/{id}")
    public ResponseEntity<TestCase> getTestCaseById(@PathVariable int id) {
        Optional<TestCase> testCase = testCaseService.getTestCaseById(id);
        return testCase.map(ResponseEntity::ok).orElseGet(() -> ResponseEntity.notFound().build());
    }

    @GetMapping("/problem/{problemId}")
    public ResponseEntity<Optional<TestCase>> getTestCasesByProblemId(@PathVariable int problemId) {
        return ResponseEntity.ok(testCaseService.getTestCasesByProblemId(problemId));
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteTestCase(@PathVariable int id) {
        testCaseService.deleteTestCase(id);
        return ResponseEntity.noContent().build();
    }
}
