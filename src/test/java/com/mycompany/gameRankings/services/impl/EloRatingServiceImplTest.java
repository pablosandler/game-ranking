package com.mycompany.gameRankings.services.impl;

import com.mycompany.gameRankings.enums.MatchResult;
import com.mycompany.gameRankings.services.EloRatingService;
import org.junit.Before;
import org.junit.Test;

import static org.junit.Assert.assertEquals;


public class EloRatingServiceImplTest {

    private int currentRating = 100;
    private int opponentRating = 200;
    private EloRatingService eloRatingService;

    @Before
    public void setup(){
        eloRatingService = new EloRatingServiceImpl();
    }

    @Test
    public void testGetNewRatingOnWonMatch(){
        int newRating = eloRatingService.getNewRating(currentRating,opponentRating, MatchResult.WON);
        assertEquals(120,newRating);
    }


    @Test
    public void testGetNewRatingOnLostMatch(){
        int newRating = eloRatingService.getNewRating(currentRating,opponentRating,MatchResult.LOST);
        assertEquals(88,newRating);
    }


    @Test
    public void testGetNewRatingOnDrawMatch(){
        int newRating = eloRatingService.getNewRating(currentRating,opponentRating,MatchResult.DRAW);
        assertEquals(104,newRating);
    }

    @Test
    public void testCalculateExpectedScore(){
        double expectedScore = eloRatingService.calculateExpectedScore(10,10);
        assertEquals(0.5,expectedScore,1e-2);

        expectedScore = eloRatingService.calculateExpectedScore(200,10);
        assertEquals(0.75,expectedScore,1e-2);

        expectedScore = eloRatingService.calculateExpectedScore(500,10);
        assertEquals(0.95,expectedScore,1e-2);

        expectedScore = eloRatingService.calculateExpectedScore(10,500);
        assertEquals(0.05,expectedScore,1e-2);
    }

}