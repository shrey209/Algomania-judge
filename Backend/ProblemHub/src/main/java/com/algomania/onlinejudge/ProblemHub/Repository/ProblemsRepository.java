package com.algomania.onlinejudge.ProblemHub.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.JpaSpecificationExecutor;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;
import org.springframework.stereotype.Repository;

import com.algomania.onlinejudge.ProblemHub.Entity.Problems;

@Repository
public interface ProblemsRepository extends JpaRepository<Problems, Integer>,JpaSpecificationExecutor<Problems> {

	 Page<Problems> findAll(Specification<Problems> specification ,Pageable pageable);
    
    @Query("SELECT p FROM Problems p WHERE p.title LIKE %:search%")
    List<Problems> findByTitleContaining(@Param("search") String search);

	Page<Problems> findByIdIn(List<Integer> problemids, Pageable pageable);
    
}
