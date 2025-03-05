package com.Algomania.users_microservice.services;

import com.Algomania.users_microservice.Entity.User;
import com.Algomania.users_microservice.Repository.UserRepository;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import reactor.core.publisher.Flux;
import reactor.core.publisher.Mono;

import java.util.List;

@Service
public class UserService {

    private final UserRepository userRepository;

    @Autowired
    public UserService(UserRepository userRepository) {
        this.userRepository = userRepository;
    }

    
    public Flux<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Mono<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public Mono<User> saveUser(User user) {
        return userRepository.save(user);
    }

    public Mono<Void> deleteUserById(String id) {
        return userRepository.deleteById(id);
    }

    public Mono<User> incrementEasyCount(String userId) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setEasyCount(user.getEasyCount() + 1);
                    return userRepository.save(user);
                });
    }

    public Mono<User> incrementMediumCount(String userId) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setMediumCount(user.getMediumCount() + 1);
                    return userRepository.save(user);
                });
    }

    public Mono<User> incrementHardCount(String userId) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.setHardCount(user.getHardCount() + 1);
                    return userRepository.save(user);
                });
    }

    public Mono<User> addSolvedProblemId(String userId, int problemId) {
        return userRepository.findById(userId)
                .flatMap(user -> {
                    user.getSolvedProblemsId().add(problemId);
                    return userRepository.save(user);
                });
    }
    public Mono<Boolean> checkIfProblemSolved(String userId, int problemId) {
        return userRepository.findById(userId)
                .map(user -> user.getSolvedProblemsId().contains(problemId));
    }

   

    public Flux<Integer> getSubsetOfSolvedProblemIds(String userId, int start, int end) {
        return userRepository.findById(userId)
                .map(User::getSolvedProblemsId)
                .flatMapMany(Flux::fromIterable)  // Flatten the set into a Flux
                .skip(start - 1)  // Skip elements up to start index (1-based index)
                .take(end - start + 1);  // Take elements from start index to end index
    }
    public Flux<Integer> getAllProblemIdsByUserId(String userId) {
        return userRepository.findById(userId)
                .map(User::getSolvedProblemsId) // Get the set of solved problem IDs
                .flatMapMany(Flux::fromIterable); // Convert set to Flux of integers
    }
}
