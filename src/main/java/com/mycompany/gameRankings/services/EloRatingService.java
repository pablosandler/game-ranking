package com.mycompany.gameRankings.services;

import com.mycompany.gameRankings.enums.MatchResult;

public interface EloRatingService {

    int getNewRating(int currentRating, int opponentRating, MatchResult score);

    double calculateExpectedScore(int rating, int opponentRating);

}
