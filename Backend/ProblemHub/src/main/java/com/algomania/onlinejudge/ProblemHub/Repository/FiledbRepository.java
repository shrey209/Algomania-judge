package com.algomania.onlinejudge.ProblemHub.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;

import com.algomania.onlinejudge.ProblemHub.Entity.Filedb;

public interface FiledbRepository extends JpaRepository<Filedb, Integer> {

	
	Optional<Filedb> findByProblemDetailsId(int problemId);
	
	 @Query("SELECT f.inputfiledata FROM Filedb f WHERE f.problemDetailsId = :problemDetailsId")
	    Optional<byte[]> findInputFileDataByProblemDetailsId(int problemDetailsId);

	    @Query("SELECT f.outputfiledata FROM Filedb f WHERE f.problemDetailsId = :problemDetailsId")
	    Optional<byte[]> findOutputFileDataByProblemDetailsId(int problemDetailsId);
	
}
