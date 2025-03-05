package com.Algomania.users_microservice.Controller;

import com.Algomania.users_microservice.Entity.SubmissionInfo;
import com.Algomania.users_microservice.Entity.Submissions;
import com.Algomania.users_microservice.services.SubmissionServices;

import lombok.AllArgsConstructor;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@RestController
@RequestMapping("/submissions")
@AllArgsConstructor
public class SubmissionsController {

     SubmissionServices submissionServices;

    @GetMapping("/user/{userId}/problem/{problemid}")
    public ResponseEntity<List<Submissions>> getAllSubmissions(@PathVariable String userId, @PathVariable int problemid) {
        List<Submissions> submissions = submissionServices.getAllSubmissionsByUserIdAndProblemId(userId, problemid);
        return ResponseEntity.ok(submissions);
    }

    @GetMapping("/info/user/{userId}/problem/{problemid}")
    public ResponseEntity<List<SubmissionInfo>> getSubmissionInfo(@PathVariable String userId, @PathVariable int problemid) {
        List<SubmissionInfo> submissionfo = submissionServices.getSubmissionStatusDateLangByUserIdAndProblemId(userId, problemid);
        return ResponseEntity.ok(submissionfo);
    }

    @GetMapping("/single/user/{userId}/problem/{problemid}")
    public ResponseEntity<Submissions> getSubmission(@PathVariable String userId, @PathVariable int problemid) {
        Submissions submission = submissionServices.getSubmissionByUserIdAndProblemId(userId, problemid);
        return submission != null ? ResponseEntity.ok(submission) : ResponseEntity.notFound().build();
    }
}
