package com.algomania.onlinejudge.ProblemHub.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algomania.onlinejudge.ProblemHub.Entity.Category;

import jakarta.transaction.Transactional;

public interface CategoryRepository extends JpaRepository<Category, Integer>{

	
	Optional<Category>findBytype(String type);
	
	 @Modifying
	    @Transactional
	    @Query("UPDATE Category c SET c.count = c.count + 1 WHERE c.id = :id")
	    void incrementCategoryCountById(@Param("id") int id);
}
