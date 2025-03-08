package com.algomania.onlinejudge.ProblemHub.Controller;

import java.util.Optional;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.client.RestTemplate;

import com.algomania.onlinejudge.ProblemHub.DTO.ExecuteDTO;
import com.algomania.onlinejudge.ProblemHub.DTO.ExecuteRes;
import com.algomania.onlinejudge.ProblemHub.DTO.ExecuteServerDTO;
import com.algomania.onlinejudge.ProblemHub.DTO.ExecuteServerResponse;
import com.algomania.onlinejudge.ProblemHub.Entity.TestCase;
import com.algomania.onlinejudge.ProblemHub.Services.TestCaseService;

@RestController
@RequestMapping("/Algomania/Execute")
public class Execution {

    @Value("${backend.url}")
    private String url;

    @Autowired
    private TestCaseService testCaseService;

    @Autowired
    private RestTemplate restTemplate;  // Inject RestTemplate

    @PostMapping("/code")
    public ExecuteRes executeCode(@RequestBody ExecuteDTO entity) {
        System.out.println("Backend URL: " + url);
        System.out.println("Received entity: " + entity);

        Optional<TestCase> optionalTestcase = testCaseService.getTestCasesByProblemId(entity.getProblem_id());
        if (optionalTestcase.isEmpty()) {
            throw new RuntimeException("Test case not found for problem ID: " + entity.getProblem_id());
        }

        TestCase testCase = optionalTestcase.get();
        ExecuteServerDTO executeServerDTO = new ExecuteServerDTO(entity.getCode(), testCase.getInput(), entity.getLang());

        System.out.println("Sending request: " + executeServerDTO);

        // Send POST request to backend
        ExecuteServerResponse response = restTemplate.postForObject(url, executeServerDTO, ExecuteServerResponse.class);
        if (response != null && response.getResponse() != null) {
            String cleanedResponse = response.getResponse()
                .replaceAll("[\\u0000-\\u0008\\u000B\\u000C\\u000E-\\u001F]", "");
            response.setResponse(cleanedResponse);
        }
        System.out.println("Cleaned response: " + response);
     	ExecuteRes executeRes=new ExecuteRes();
        if(testCase.getOutput().equals(response.getResponse())){
        	executeRes.setAccepted(true);
        	executeRes.setExpected(testCase.getOutput());
        	executeRes.setResponse(response.getResponse());
        	executeRes.setError(false);
        	return executeRes;
        };
        
       	executeRes.setAccepted(false);
    	executeRes.setExpected(testCase.getOutput());
    	executeRes.setResponse(response.getResponse());
    	if(response.isError()==true)
    	executeRes.setError(true);
    	
    	return executeRes;
    }
}
