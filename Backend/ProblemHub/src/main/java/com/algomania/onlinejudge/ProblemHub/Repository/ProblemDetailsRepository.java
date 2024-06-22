package com.algomania.onlinejudge.ProblemHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algomania.onlinejudge.ProblemHub.Entity.ProblemDetails;

public interface ProblemDetailsRepository extends JpaRepository<ProblemDetails, Integer> {

}
