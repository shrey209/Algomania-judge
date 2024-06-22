package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.List;
import java.util.Optional;


import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algomania.onlinejudge.ProblemHub.Entity.Category;
import com.algomania.onlinejudge.ProblemHub.Repository.CategoryRepository;

@Service
public class CategoryServices {

	CategoryRepository categoryRepository;
	
	CategoryServices(CategoryRepository categoryRepository){
		this.categoryRepository=categoryRepository;
	}
	
	
	//Add category 
	Category AddCategory(String type) {
		return categoryRepository.save(new Category(0,type,0));
	}
	
	//search by category type
	public Optional<Category> SearchBytype(String type) {
		return categoryRepository.findBytype(type);
	}
	
	//Increment the count 
	 public void incrementCategoryCount(int id) {
	        categoryRepository.incrementCategoryCountById(id);
	    }
	 
	 
	 //Get count of a category based on its id
   public int getcountById(int id) {
	   return categoryRepository.findById(id).get().getCount();
   }

   //Get all categories
   public List<Category> getAllCategories() {
       return categoryRepository.findAll();
   }
	
	
}
