package com.algomania.onlinejudge.ProblemHub.Repository;

import java.util.List;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algomania.onlinejudge.ProblemHub.Entity.SampleTestCase;

public interface SampleTestRepository extends JpaRepository<SampleTestCase, Integer> {
	 List<SampleTestCase> findByProblemDetailsId(int problemDetailsId);


}
