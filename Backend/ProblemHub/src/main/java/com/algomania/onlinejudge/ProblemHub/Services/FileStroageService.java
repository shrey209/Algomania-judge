package com.algomania.onlinejudge.ProblemHub.Services;

import java.io.IOException;
import java.util.Optional;

import org.springframework.stereotype.Service;
import org.springframework.util.StringUtils;
import org.springframework.web.multipart.MultipartFile;

import com.algomania.onlinejudge.ProblemHub.Entity.Filedb;
import com.algomania.onlinejudge.ProblemHub.Repository.FiledbRepository;

@Service
public class FileStroageService {

	FiledbRepository filedbRepository;
	
	public FileStroageService(FiledbRepository filedbRepository) {
	this.filedbRepository=filedbRepository;
	}
	
	 public Filedb store(int problemid, MultipartFile file1,MultipartFile file2) throws IOException {
		    String fileName1 = StringUtils.cleanPath(file1.getOriginalFilename());
		    String fileName2 = StringUtils.cleanPath(file2.getOriginalFilename());
		    Filedb FileDB = new Filedb(0,problemid,fileName1 ,fileName2,file1.getBytes(),file2.getBytes());

		    return filedbRepository.save(FileDB);
		  }
	 
	 public Optional<Filedb> getFileById(int id) {
	        return filedbRepository.findById(id);
	    }
	
	 public Optional<Filedb>getfilebyproblemids(int problemid){
		 return filedbRepository.findByProblemDetailsId(problemid);
	 }
	 
	 public Optional<byte[]> getInputFileDataByProblemDetailsId(int problemDetailsId) {
	        return filedbRepository.findInputFileDataByProblemDetailsId(problemDetailsId);
	    }

	    public Optional<byte[]> getOutputFileDataByProblemDetailsId(int problemDetailsId) {
	        return filedbRepository.findOutputFileDataByProblemDetailsId(problemDetailsId);
	    }
	    
	    
	 
}
