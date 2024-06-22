package com.algomania.onlinejudge.ProblemHub.Services;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import com.algomania.onlinejudge.ProblemHub.Entity.Boilerplate;
import com.algomania.onlinejudge.ProblemHub.Entity.ProblemDetails;
import com.algomania.onlinejudge.ProblemHub.Repository.BoilerplateRepository;


/*
 * this service class includes methods for 
 * 1)finding by id 
 * 2)finding entry based on problem detail id and language type
*/

@Service
public class BoilerPlateServices {
BoilerplateRepository boilerplateRepository;

public BoilerPlateServices(BoilerplateRepository boilerplateRepository) {
	this.boilerplateRepository=boilerplateRepository;
}
//add boilerplates
Boilerplate AddBoilerplate(int problemInfoId,String lang,String code) {
	return boilerplateRepository.save(new Boilerplate(0, problemInfoId, lang, code));
}

// search boilerplate by problemdetailsid and language
public Optional<Boilerplate> findByProblemDetailsIdAndLanguageType(int problemDetailsId, String languageType) {
    return boilerplateRepository.findByProblemDetailsIdAndLanguageType(problemDetailsId, languageType);
}
	
}