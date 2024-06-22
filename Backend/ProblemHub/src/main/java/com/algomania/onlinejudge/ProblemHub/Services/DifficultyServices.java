package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.List;
import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algomania.onlinejudge.ProblemHub.Entity.Difficulty;
import com.algomania.onlinejudge.ProblemHub.Repository.DifficultyRepository;

import jakarta.transaction.Transactional;

@Service
public class DifficultyServices {

	DifficultyRepository difficultyRepository;
	

	DifficultyServices(DifficultyRepository difficultyRepository){
		this.difficultyRepository=difficultyRepository;
	}
	
	//Add difficulty
	Difficulty    AddDifficulty(String level) {
		return difficultyRepository.save(new Difficulty(0,level,0));
	}
	
	//find difficulty by level
	public Optional<Difficulty> searchByDifficulty(String level) {
		return difficultyRepository.findBylevel(level);
	}
	
	// increment the counter 
	 @Transactional
	 public void incrementDifficultyCount(int id) {
	        difficultyRepository.incrementProblemCountById(id);
	    }
	 
	 //get the difficulty count by id
	 public int getDifficultyCount(int id) {
		return difficultyRepository.findById(id).get().getCount();
		 
	 }
	 //get All difficulty
	 public List<Difficulty>getallDifficulties(){
		 return difficultyRepository.findAll();
	 }
	
}