package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.List;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.stereotype.Service;
import com.algomania.onlinejudge.ProblemHub.Entity.Problems;
import com.algomania.onlinejudge.ProblemHub.Repository.ProblemsRepository;

@Service
public class ProblemsServices {

    private final ProblemsRepository problemsRepository;
    private static final int DEFAULT_PAGE_SIZE = 10;

    public ProblemsServices(ProblemsRepository problemsRepository) {
        this.problemsRepository = problemsRepository;
    }

    // Add problem
    public Problems addProblem(int problemDetailsId, int difficultyId, int categoryId, String title) {
        return problemsRepository.save(new Problems(0, problemDetailsId, difficultyId, categoryId, 0, title));
    }

    // Search problems by id
    public Optional<Problems> searchProblemsById(int id) {
        return problemsRepository.findById(id);
    }

    // Pagination to display problems
    public Page<Problems> getProblemsPaginated(int page) {
        Pageable pageable = PageRequest.of(page - 1, DEFAULT_PAGE_SIZE);
        return problemsRepository.findAll(pageable);
    }

    public Page<Problems>getproblemsbylistofid(List<Integer>problemids,int page){
    	Pageable pageable=PageRequest.of(page-1,DEFAULT_PAGE_SIZE);
    	return problemsRepository.findByIdIn(problemids, pageable);
    }
    
    // GET all problems by category id
    public Page<Problems> findProblemsByDifficultyAndCategoryExcludingIds(
            List<Integer> difficultyIds, List<Integer> categoryIds, List<Integer> excludedProblemIds, int page) {
        Pageable pageable = PageRequest.of(page - 1, DEFAULT_PAGE_SIZE);

        // Case: All three arrays are empty
        if (difficultyIds.isEmpty() && categoryIds.isEmpty() && excludedProblemIds.isEmpty()) {
            return problemsRepository.findAll(pageable);
        }

        // Case: difficultyIds and categoryIds are empty but excludedProblemIds is not
        if (difficultyIds.isEmpty() && categoryIds.isEmpty() && !excludedProblemIds.isEmpty()) {
            return problemsRepository.findByIdNotIn(excludedProblemIds, pageable);
        }

        // Case: difficultyIds is empty but categoryIds and excludedProblemIds are not
        if (difficultyIds.isEmpty() && !categoryIds.isEmpty() && !excludedProblemIds.isEmpty()) {
            return problemsRepository.findByCategoryIdInAndIdNotIn(categoryIds, excludedProblemIds, pageable);
        }

        // Case: categoryIds is empty but difficultyIds and excludedProblemIds are not
        if (!difficultyIds.isEmpty() && categoryIds.isEmpty() && !excludedProblemIds.isEmpty()) {
            return problemsRepository.findByDifficultyIdInAndIdNotIn(difficultyIds, excludedProblemIds, pageable);
        }

        // Case: Only difficultyIds provided
        if (!difficultyIds.isEmpty() && categoryIds.isEmpty() && excludedProblemIds.isEmpty()) {
            return problemsRepository.findByDifficultyIdIn(difficultyIds, pageable);
        }

        // Case: Only categoryIds provided
        if (difficultyIds.isEmpty() && !categoryIds.isEmpty() && excludedProblemIds.isEmpty()) {
            return problemsRepository.findByCategoryIdIn(categoryIds, pageable);
        }

        // Case: Only excludedProblemIds provided
        if (difficultyIds.isEmpty() && categoryIds.isEmpty() && !excludedProblemIds.isEmpty()) {
            return problemsRepository.findByIdNotIn(excludedProblemIds, pageable);
        }

        // Case: difficultyIds and categoryIds provided
        if (!difficultyIds.isEmpty() && !categoryIds.isEmpty() && excludedProblemIds.isEmpty()) {
            return problemsRepository.findByDifficultyIdInAndCategoryIdIn(difficultyIds, categoryIds, pageable);
        }

        // Case: difficultyIds, categoryIds, and excludedProblemIds provided
        if (!difficultyIds.isEmpty() && !categoryIds.isEmpty() && !excludedProblemIds.isEmpty()) {
            return problemsRepository.findByDifficultyIdInAndCategoryIdInAndIdNotIn(difficultyIds, categoryIds, excludedProblemIds, pageable);
        }

        throw new IllegalArgumentException("Unexpected case");
    }


}
