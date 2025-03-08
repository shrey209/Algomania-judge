package com.algomania.onlinejudge.ProblemHub.Services;

import org.springframework.stereotype.Service;
import com.algomania.onlinejudge.ProblemHub.Entity.TestCase;
import com.algomania.onlinejudge.ProblemHub.Repository.TestCaseRepository;

import java.util.List;
import java.util.Optional;

@Service
public class TestCaseService {

    private final TestCaseRepository testCaseRepository;

    public TestCaseService(TestCaseRepository testCaseRepository) {
        this.testCaseRepository = testCaseRepository;
    }

    public TestCase addTestCase(TestCase testCase) {
        return testCaseRepository.save(testCase);
    }

    public Optional<TestCase> getTestCasesByProblemId(int problemId) {
        return testCaseRepository.findByProblemId(problemId);
    }

    public Optional<TestCase> getTestCaseById(int id) {
        return testCaseRepository.findById(id);
    }

    public void deleteTestCase(int id) {
        testCaseRepository.deleteById(id);
    }
}
