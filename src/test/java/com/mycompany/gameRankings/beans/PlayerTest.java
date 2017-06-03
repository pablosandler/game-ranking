package com.mycompany.gameRankings.beans;

import com.mycompany.gameRankings.enums.MatchResult;
import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertTrue;

public class PlayerTest {

    @Test
    public void testConstructor(){
        String playerName = "MyPlayer";
        long playerId = 1l;

        Player player = new Player(playerId,playerName);

        assertTrue(playerId == player.getId());
        assertEquals(playerName,player.getName());
        assertTrue(0 == player.getLostMatches());
        assertTrue(0 == player.getWonMatches());
        assertTrue(0 == player.getRating());
    }


    @Test
    public void testAddPlayedMatch(){
        String playerName = "MyPlayer";
        long playerId = 1l;

        Player player = new Player(playerId,playerName);

        player.addPlayedMatch(MatchResult.WON);
        assertTrue(0 == player.getLostMatches());
        assertTrue(1 == player.getWonMatches());

        player.addPlayedMatch(MatchResult.LOST);
        assertTrue(1 == player.getLostMatches());
        assertTrue(1 == player.getWonMatches());
    }

}