package com.Algomania.users_microservice.services;

import org.springframework.stereotype.Service;

import com.Algomania.users_microservice.Entity.SubmissionInfo;
import com.Algomania.users_microservice.Entity.Submissions;
import com.Algomania.users_microservice.Repository.SubmissionsRepo;
import lombok.AllArgsConstructor;
import java.util.List;
import java.util.stream.Collectors;

@Service
@AllArgsConstructor
public class SubmissionServices {

    private final SubmissionsRepo submissionsRepo;

    public List<Submissions> getAllSubmissionsByUserIdAndProblemId(String userId, int problemid) {
        return submissionsRepo.findByUserIdAndProblemid(userId, problemid);
    }

    public List<SubmissionInfo> getSubmissionStatusDateLangByUserIdAndProblemId(String userId, int problemid) {
        return submissionsRepo.findByUserIdAndProblemid(userId, problemid)
                .stream()
                .map(submission -> new SubmissionInfo(submission.getStatus(), submission.getDate(), submission.getLang()))
                .collect(Collectors.toList());
    }

    public Submissions getSubmissionByUserIdAndProblemId(String userId, int problemid) {
        List<Submissions> submissions = submissionsRepo.findByUserIdAndProblemid(userId, problemid);
        return submissions.isEmpty() ? null : submissions.get(0);
    }

}
