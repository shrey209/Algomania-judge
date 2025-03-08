package com.algomania.onlinejudge.ProblemHub.DTO;

import java.util.List;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class ProblemsFilterDTO {

	  private List<Integer> difficultyIds;
	    private List<Integer> categoryIds;
	   private boolean solved;
	    private int page;
	    
}
