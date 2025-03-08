package com.algomania.onlinejudge.ProblemHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.algomania.onlinejudge.ProblemHub.Entity.TestCase;
import java.util.List;
import java.util.Optional;

@Repository
public interface TestCaseRepository extends JpaRepository<TestCase, Integer> {
    Optional<TestCase> findByProblemId(int problemId);
}
