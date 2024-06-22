package com.Algomania.users_microservice.Repository;

import org.springframework.data.mongodb.repository.MongoRepository;
import org.springframework.data.mongodb.repository.ReactiveMongoRepository;
import org.springframework.stereotype.Repository;

import com.Algomania.users_microservice.Entity.Submissions;
import java.util.List;

@Repository
public interface SubmissionsRepo extends  ReactiveMongoRepository<Submissions, String> {
    List<Submissions> findByUserIdAndProblemid(String userId, int problemid);
}
