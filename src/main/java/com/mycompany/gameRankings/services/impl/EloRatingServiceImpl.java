package com.mycompany.gameRankings.services.impl;

import com.mycompany.gameRankings.enums.MatchResult;
import com.mycompany.gameRankings.services.EloRatingService;


public class EloRatingServiceImpl implements EloRatingService {

    public static final int K_FACTOR = 32;


    public int getNewRating(int currentRating, int opponentRating, MatchResult score) {
        double expectedScore = calculateExpectedScore(currentRating, opponentRating);
        return calculateNewRating(currentRating, score, expectedScore);
    }


    public double calculateExpectedScore(int rating, int opponentRating) {
        double calculation = (opponentRating - rating) / 400.0;
        return 1.0 / (1.0 + Math.pow(10.0, calculation));
    }


    private int calculateNewRating(int rating, MatchResult score, double expectedScore) {
        return rating + (int) Math.round(K_FACTOR * (score.getValue() - expectedScore));
    }


}
