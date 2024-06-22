package com.Algomania.Leaderboard.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Algomania.Leaderboard.Services.LeaderboardService;

import java.util.List;
import java.util.Set;
import com.Algomania.Leaderboard.DTOS.TopScoreDTO;
@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToLeaderboard(@RequestParam String userId) {
        leaderboardService.addToLeaderboard(userId, 0);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added to leaderboard successfully");
    }

    @GetMapping("/top/{count}")
    public ResponseEntity<Set<String>> getTopPlayers(@PathVariable int count) {
        Set<String> topPlayers = leaderboardService.getTopPlayers(count);
        return ResponseEntity.ok(topPlayers);
    }

    @GetMapping("/rank/{userId}")
    public ResponseEntity<Long> getRank(@PathVariable String userId) {
        try {
            Long rank = leaderboardService.getRank(userId);
            if (rank == null) {
                throw new Exception("User with id " + userId + " not found");
            }
            return ResponseEntity.ok(rank);
        } catch (Exception ex) {
            // Wrap the exception in ResponseEntity with NOT_FOUND status
            return ResponseEntity.status(HttpStatus.NOT_FOUND).body(null);
        }
    }

    @GetMapping("/score/{userId}")
    public ResponseEntity<Double> getScore(@PathVariable String userId) {
        Double score = leaderboardService.getScore(userId);
        return ResponseEntity.ok(score);
    }
    
    @GetMapping("/top-scores/{count}")
    public ResponseEntity<List<TopScoreDTO>> getTopScores(@PathVariable int count) {
        List<TopScoreDTO> topScores = leaderboardService.getTopScores(count);
        return ResponseEntity.ok(topScores);
    }
    
    @PostMapping("/increment-score")
    public ResponseEntity<String> incrementUserScore(@RequestParam String userId, @RequestParam double incrementValue) {
        leaderboardService.incrementUserScore(userId, incrementValue);
        return ResponseEntity.ok("Score for user " + userId + " incremented by " + incrementValue);
    }

    // Other endpoints for additional leaderboard operations (e.g., get scores, remove members, etc.)
}

