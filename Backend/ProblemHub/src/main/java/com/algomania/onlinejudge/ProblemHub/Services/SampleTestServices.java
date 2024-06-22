

package com.algomania.onlinejudge.ProblemHub.Services;

import org.springframework.stereotype.Service;
import com.algomania.onlinejudge.ProblemHub.Repository.SampleTestRepository;
import com.algomania.onlinejudge.ProblemHub.DTO.SampleTestDTO;
import com.algomania.onlinejudge.ProblemHub.Entity.ProblemDetails;
import com.algomania.onlinejudge.ProblemHub.Entity.SampleTestCase;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class SampleTestServices {

    private final SampleTestRepository sampleTestRepository;

    public SampleTestServices(SampleTestRepository sampleTestRepository) {
        this.sampleTestRepository = sampleTestRepository;
    }

    public List<SampleTestDTO> getSampleTestCasesByProblemDetailsId(int problemDetailsId) {
        List<SampleTestCase> sampleTestCases = sampleTestRepository.findByProblemDetailsId(problemDetailsId);
        
        List<SampleTestDTO> sampleTestDTOs = sampleTestCases.stream()
            .map(sampleTestCase -> SampleTestDTO.builder()
                .input(sampleTestCase.getInput())
                .output(sampleTestCase.getOutput())
                .explanation(sampleTestCase.getExplanation())
                .build())
            .collect(Collectors.toList());
        
        return sampleTestDTOs;
    }
    
    public void addSampleTestCase(SampleTestDTO sampleTestDTO, ProblemDetails problemDetails) {
      sampleTestRepository.save(new SampleTestCase(0,sampleTestDTO.getInput(),sampleTestDTO.getOutput(),sampleTestDTO.getExplanation(),problemDetails));
    }
    
    public List<SampleTestCase> saveAllSampleTestCases(List<SampleTestDTO> sampleTestDTOs, ProblemDetails problemDetails) {
        List<SampleTestCase> sampleTestCases = new ArrayList<>();
        for (SampleTestDTO it : sampleTestDTOs) {
            sampleTestCases.add(new SampleTestCase(0, it.getInput(), it.getOutput(), it.getExplanation(), problemDetails));
        }
        return sampleTestRepository.saveAll(sampleTestCases);
    }
    
}
