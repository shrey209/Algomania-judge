package com.algomania.onlinejudge.ProblemHub.Repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Modifying;
import org.springframework.data.jpa.repository.Query;
import org.springframework.data.repository.query.Param;

import com.algomania.onlinejudge.ProblemHub.Entity.Difficulty;

import jakarta.transaction.Transactional;

public interface DifficultyRepository extends JpaRepository<Difficulty, Integer>{

	Optional<Difficulty> findBylevel(String level);

	 @Modifying
	    @Transactional
	    @Query("UPDATE Difficulty d SET d.count = d.count + 1 WHERE d.id = :id")
	    void incrementProblemCountById(@Param("id") int id);

}
