package com.mycompany.gameRankings.beans;

import com.mycompany.gameRankings.enums.MatchResult;
import org.junit.Assert;
import org.junit.Test;

import static org.junit.Assert.assertEquals;

public class MatchTest {

    @Test
    public void testConstructorWithPlayerOneAsWinner(){
        Match match = new Match(1l,2l, MatchResult.WON);

        assertEquals(1,match.getPlayerOneId());
        assertEquals(2,match.getPlayerTwoId());
        Assert.assertEquals(MatchResult.WON, match.getMatchResultForPlayerOne());
        Assert.assertEquals(MatchResult.LOST, match.getMatchResultForPlayerTwo());
    }


    @Test
    public void testConstructorWithPlayerOneAsLoser(){
        Match match = new Match(1l,2l,MatchResult.LOST);

        assertEquals(1,match.getPlayerOneId());
        assertEquals(2,match.getPlayerTwoId());
        Assert.assertEquals(MatchResult.LOST, match.getMatchResultForPlayerOne());
        Assert.assertEquals(MatchResult.WON, match.getMatchResultForPlayerTwo());
    }

    @Test
    public void testConstructorWithDrawMatch(){
        Match match = new Match(1l,2l,MatchResult.DRAW);

        assertEquals(1,match.getPlayerOneId());
        assertEquals(2,match.getPlayerTwoId());
        Assert.assertEquals(MatchResult.DRAW, match.getMatchResultForPlayerOne());
        Assert.assertEquals(MatchResult.DRAW, match.getMatchResultForPlayerTwo());
    }

}