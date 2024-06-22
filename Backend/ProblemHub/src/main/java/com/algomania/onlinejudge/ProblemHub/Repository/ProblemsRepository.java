package com.algomania.onlinejudge.ProblemHub.Repository;

import java.util.List;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;
import com.algomania.onlinejudge.ProblemHub.Entity.Problems;

@Repository
public interface ProblemsRepository extends JpaRepository<Problems, Integer> {

    List<Problems> findByCategoryId(int categoryId);

    List<Problems> findByDifficultyId(int difficultyId);

    List<Problems> findByCategoryIdAndDifficultyId(int categoryId, int difficultyId);

    Page<Problems> findByIdIn(List<Integer> ids, Pageable pageable);
    
    Page<Problems> findByCategoryIdIn(List<Integer> categoryIds, Pageable pageable);

    @Query("SELECT p FROM Problems p WHERE p.difficultyId IN :difficultyIds")
    Page<Problems> findByDifficultyIdIn(List<Integer> difficultyIds, Pageable pageable);

    @Query("SELECT p FROM Problems p WHERE p.difficultyId IN :difficultyIds AND p.id NOT IN :problemIds")
    Page<Problems> findByDifficultyIdInAndIdNotIn(
        List<Integer> difficultyIds,
        List<Integer> problemIds,
        Pageable pageable
    );

    @Query("SELECT p FROM Problems p WHERE p.difficultyId IN :difficultyIds AND p.categoryId IN :categoryIds")
    Page<Problems> findByDifficultyIdInAndCategoryIdIn(
        List<Integer> difficultyIds,
        List<Integer> categoryIds,
        Pageable pageable
    );

    @Query("SELECT p FROM Problems p WHERE p.difficultyId IN :difficultyIds AND p.categoryId IN :categoryIds AND p.id NOT IN :problemIds")
    Page<Problems> findByDifficultyIdInAndCategoryIdInAndIdNotIn(
        List<Integer> difficultyIds,
        List<Integer> categoryIds,
        List<Integer> problemIds,
        Pageable pageable
    );

    @Query("SELECT p FROM Problems p WHERE p.categoryId IN :categoryIds AND p.id NOT IN :problemIds")
    Page<Problems> findByCategoryIdInAndIdNotIn(
        List<Integer> categoryIds,
        List<Integer> problemIds,
        Pageable pageable
    );

    @Query("SELECT p FROM Problems p WHERE p.id NOT IN :problemIds")
    Page<Problems> findByIdNotIn(
        List<Integer> problemIds,
        Pageable pageable
    );
}
