package com.algomania.onlinejudge.ProblemHub.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algomania.onlinejudge.ProblemHub.Entity.Boilerplate;

public interface BoilerplateRepository extends JpaRepository<Boilerplate, Integer>{

	 @Query("SELECT b FROM Boilerplate b WHERE b.problemDetailsId = :problemDetailsId AND b.languageType = :languageType")
	    Optional<Boilerplate> findByProblemDetailsIdAndLanguageType(@Param("problemDetailsId") int problemDetailsId, @Param("languageType") String languageType);
}
