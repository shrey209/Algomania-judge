package com.Algomania.Leaderboard.DTOS;

import lombok.AllArgsConstructor;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@AllArgsConstructor
@NoArgsConstructor
public class TopScoreDTO {
String playerId;
int score;
}
