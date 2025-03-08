package com.Algomania.Leaderboard.Controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import com.Algomania.Leaderboard.Services.LeaderboardService;
import com.Algomania.Leaderboard.DTOS.TopScoreDTO;

import java.util.List;
import java.util.Set;

@RestController
@RequestMapping("/leaderboard")
public class LeaderboardController {

    private final LeaderboardService leaderboardService;

    @Autowired
    public LeaderboardController(LeaderboardService leaderboardService) {
        this.leaderboardService = leaderboardService;
    }

    @PostMapping("/add")
    public ResponseEntity<String> addToLeaderboard( @RequestParam String userId, @RequestParam String username) {
        leaderboardService.addToLeaderboard(userId, username, 0);
        return ResponseEntity.status(HttpStatus.CREATED).body("User added to leaderboard successfully");
    }

    @GetMapping("/top/{count}")
    public ResponseEntity<Set<String>> getTopPlayers(@PathVariable int count) {
        Set<String> topPlayers = leaderboardService.getTopPlayers(count);
        return ResponseEntity.ok(topPlayers);
    }

    @GetMapping("/rank")
    public ResponseEntity<Long> getRank( @RequestHeader(value = "X-User-ID", required = false) String userId, @RequestParam String username) {
        Long rank = leaderboardService.getRank(userId, username);
        if (rank == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(rank);
    }

    @GetMapping("/score")
    public ResponseEntity<Double> getScore( @RequestHeader(value = "X-User-ID", required = false) String userId, @RequestParam String username) {
        Double score = leaderboardService.getScore(userId, username);
        if (score == null) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body(null);
        }
        return ResponseEntity.ok(score);
    }

    @GetMapping("/top-scores/{count}")
    public ResponseEntity<List<TopScoreDTO>> getTopScores(@PathVariable int count) {
        List<TopScoreDTO> topScores = leaderboardService.getTopScores(count);
        return ResponseEntity.ok(topScores);
    }

    @PostMapping("/increment-score")
    public ResponseEntity<String> incrementUserScore(
    		 @RequestHeader(value = "X-User-ID", required = false) String userId, 
            @RequestParam String username, 
            @RequestParam double incrementValue) {
        
        leaderboardService.incrementUserScore(userId, username, incrementValue);
        return ResponseEntity.ok("Score for user " + userId + " incremented by " + incrementValue);
    }

    // Other endpoints for additional leaderboard operations (e.g., get scores, remove members, etc.)
}
