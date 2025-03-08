package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Pageable;
import org.springframework.data.jpa.domain.Specification;
import org.springframework.stereotype.Service;
import org.springframework.web.client.RestTemplate;

import com.algomania.onlinejudge.ProblemHub.Entity.Problems;
import com.algomania.onlinejudge.ProblemHub.Repository.ProblemsRepository;

import jakarta.persistence.criteria.CriteriaBuilder;
import jakarta.persistence.criteria.Predicate;

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
            List<Integer> difficultyIds,
            List<Integer> categoryIds,
            boolean isSolved,
            String userId,
            int page) {
        Pageable pageable = PageRequest.of(page - 1, DEFAULT_PAGE_SIZE);
        
        if(isSolved==true) {
        	 String url = "http://localhost:8012/users/{userId}/problems";
        	  RestTemplate restTemplate = new RestTemplate();
        	  List<Integer> problemIdsList = restTemplate.getForObject(url, List.class, userId);
        	  System.out.println("Response: " + problemIdsList);
        	  
        	  Page<Problems>result = problemsRepository.findAll((Specification<Problems>) (root, query, cb) -> {
        		  List<Predicate> predicates = new ArrayList<>();
        		  
        		  if(Objects.nonNull(difficultyIds) && !difficultyIds.isEmpty()) {
        			  predicates.add(root.get("difficultyId").in(difficultyIds));
        		  }
        		  if (Objects.nonNull(categoryIds) && !categoryIds.isEmpty()) {
       
        		        predicates.add(root.get("categoryId").in(categoryIds));
        		    }
        		  
        		  if (Objects.nonNull(problemIdsList) && !problemIdsList.isEmpty()) {
        			    predicates.add(cb.not(root.get("Id").in(problemIdsList)));
        			}
        		  
        		  return cb.and(predicates.toArray(new Predicate[0]));
        	  },pageable);  
        	  return result;
        }
        else {
        	 Page<Problems>result = problemsRepository.findAll((Specification<Problems>) (root, query, cb) -> {
       		  List<Predicate> predicates = new ArrayList<>();
       		  
       		  if(Objects.nonNull(difficultyIds) && !difficultyIds.isEmpty()) {
       			  predicates.add(root.get("difficultyId").in(difficultyIds));
       		  }
       		  if (Objects.nonNull(categoryIds) && !categoryIds.isEmpty()) {
      
       		        predicates.add(root.get("categoryId").in(categoryIds));
       		    }
     
       		  return cb.and(predicates.toArray(new Predicate[0]));
       	  },pageable);  
        	 return result;
        }
    }
    
    public List<Problems> searchByTitle(String search) {
        if (search.length() > 4) {
            String capitalizedSearch = search.substring(0, 1).toUpperCase() + search.substring(1);
            return problemsRepository.findByTitleContaining(capitalizedSearch);
        } else {
            throw new IllegalArgumentException("Search string must be greater than 4 characters");
        }
    }


}
