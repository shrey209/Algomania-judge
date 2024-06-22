package com.Algomania.Leaderboard.Services;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ZSetOperations;
import org.springframework.stereotype.Service;

import com.Algomania.Leaderboard.DTOS.TopScoreDTO;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;

@Service
public class LeaderboardService {

    private final ZSetOperations<String, String> zSetOperations;

    @Autowired
    public LeaderboardService(RedisTemplate<String, String> redisTemplate) {
        this.zSetOperations = redisTemplate.opsForZSet();
    }

    public void addToLeaderboard(String userId, double score) {
        String leaderboardKey = "leaderboard";
        zSetOperations.add(leaderboardKey, userId, score);
    }

    public Set<String> getTopPlayers(int count) {
        String leaderboardKey = "leaderboard";
        return zSetOperations.reverseRange(leaderboardKey, 0, count - 1);
    }

    public Long getRank(String userId) {
        String leaderboardKey = "leaderboard";
        return zSetOperations.reverseRank(leaderboardKey, userId);
    }

    public Double getScore(String userId) {
        String leaderboardKey = "leaderboard";
        return zSetOperations.score(leaderboardKey, userId);
    }

    public List<TopScoreDTO> getTopScores(int count) {
        String leaderboardKey = "leaderboard";
        Set<ZSetOperations.TypedTuple<String>> topPlayersWithScores =
                zSetOperations.reverseRangeWithScores(leaderboardKey, 0, count - 1);

        List<TopScoreDTO> topScores = new ArrayList<>();
        topPlayersWithScores.forEach(tuple ->
                topScores.add(new TopScoreDTO(tuple.getValue(), tuple.getScore().intValue())));

        return topScores;
    }
    
    public void incrementUserScore(String userId, double incrementValue) {
        String leaderboardKey = "leaderboard";
        zSetOperations.incrementScore(leaderboardKey, userId, incrementValue);
    }
    
    // Other leaderboard operations (e.g., get scores, remove members, etc.)
}
