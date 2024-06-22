package com.algomania.onlinejudge.ProblemHub.Repository;

import org.springframework.data.jpa.repository.JpaRepository;

import com.algomania.onlinejudge.ProblemHub.Entity.Solution;

public interface solutionsRepository extends JpaRepository<Solution, Integer> {

}
