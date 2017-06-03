package com.mycompany.gameRankings.userInterface.output.impl;

import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.beans.MatchResultForPlayer;
import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.enums.MatchResult;
import com.mycompany.gameRankings.userInterface.output.OutputWriter;
import org.junit.Before;
import org.junit.Test;

import java.io.ByteArrayOutputStream;
import java.io.PrintStream;
import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.assertEquals;

public class OutputWriterCommandLineImplTest {

    private ByteArrayOutputStream outContent;
    private OutputWriter outputWriter;

    @Before
    public void setup(){
        outContent = new ByteArrayOutputStream();
        System.setOut(new PrintStream(outContent));

        outputWriter = new OutputWriterCommandLineImpl();
    }


    @Test
    public void testOutputRank(){
        outputWriter.outputRank(1l,2);
        assertEquals("Rank for player 1 : 2",outContent.toString());
    }


    @Test
    public void testOutputResultsForPlayer(){
        List<MatchResultForPlayer> matchResultsForPlayer = new ArrayList<MatchResultForPlayer>();
        MatchResultForPlayer matchResultForPlayer = new MatchResultForPlayer(2l, MatchResult.WON);
        MatchResultForPlayer matchResultForPlayer2 = new MatchResultForPlayer(3L,MatchResult.LOST);

        matchResultsForPlayer.add(matchResultForPlayer);
        matchResultsForPlayer.add(matchResultForPlayer2);

        outputWriter.outputResultsForPlayer(1l, matchResultsForPlayer);
        assertEquals("Results for player 1 : \n" +
                      "Opponent: 2      Result: WON\n" +
                      "Opponent: 3      Result: LOST\n",outContent.toString());
    }

    @Test
    public void testOutputRanking(){
        List<Player> players = new ArrayList<Player>();

        Player player =  new Player(1l, "Player one");
        player.setRating(10);
        player.addPlayedMatch(MatchResult.WON);

        Player player2 =  new Player(2l, "Player two");
        player2.setRating(1);
        player2.addPlayedMatch(MatchResult.LOST);

        players.add(player);
        players.add(player2);

        outputWriter.outputRanking(players);
        assertEquals("Ranking : \n" +
                     "1  Name: Player one  Wins: 1  Losses: 0  Score: 10\n" +
                     "2  Name: Player two  Wins: 0  Losses: 1  Score: 1\n",outContent.toString());
    }

    @Test
    public void outputSuggestedNewMatches(){
        List<Match> matches = new ArrayList<Match>();

        Match match = new Match(1l,2l,MatchResult.WON);
        Match match2 = new Match(2l,3l,MatchResult.LOST);

        matches.add(match);
        matches.add(match2);

        outputWriter.outputSuggestedNewMatches(matches);
        assertEquals("Suggested matches : \n" +
                     "1  vs. 2\n" +
                     "2  vs. 3\n",outContent.toString());
    }


}