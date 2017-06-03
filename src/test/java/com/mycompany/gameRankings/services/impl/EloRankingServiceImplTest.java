package com.mycompany.gameRankings.services.impl;

import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.beans.MatchResultForPlayer;
import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.daos.MatchDao;
import com.mycompany.gameRankings.daos.PlayerDao;
import com.mycompany.gameRankings.enums.MatchResult;
import com.mycompany.gameRankings.exceptions.ApplicationException;
import com.mycompany.gameRankings.services.EloRankingService;
import com.mycompany.gameRankings.services.EloRatingService;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import java.util.ArrayList;
import java.util.List;

import static org.junit.Assert.*;
import static org.mockito.Mockito.mock;
import static org.mockito.Mockito.when;


public class EloRankingServiceImplTest {

    private EloRankingService eloRankingService;

    private MatchDao matchDao;
    private PlayerDao playerDao;
    private EloRatingService eloRatingService;

    private List<Match> matches;


    @Before
    public void setup(){
        matchDao = mock(MatchDao.class);
        playerDao = mock(PlayerDao.class);

        matches = createMatches();

        eloRatingService = new EloRatingServiceImpl();

        try {
            when(matchDao.getAll()).thenReturn(matches);
        } catch (ApplicationException e) {
            fail();
        }
    }


    @Test
    public void testGetRankRunsSuccessfully(){
        int rank = 0;

        try {
            eloRankingService = new EloRankingServiceImpl(matchDao,playerDao,eloRatingService);
            rank = eloRankingService.getRank(1L);
        } catch (ApplicationException e) {
            fail();
        }

        assertEquals(46,rank);
    }


    @Test
    public void getRankFailsWhenNonExistentPlayerIdIsSent(){
        Long playerId = 180l;

        try {
            when(playerDao.getPlayer(playerId)).thenThrow(new ApplicationException("Error"));
        } catch (ApplicationException e) {
            fail();
        }

        eloRankingService = new EloRankingServiceImpl(null,playerDao,null);

        try {
            eloRankingService.getRank(playerId);
            fail();
        } catch (ApplicationException e) {
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertEquals("Error",e.getMessage());
        }
    }


    @Test
    public void testGetRankingReturnsListOfPlayersOrderedByScore(){
        try {
            Player playerOne = new Player(1l,"Player 1");
            when(playerDao.getPlayer(1L)).thenReturn(playerOne);

            Player playerTwo = new Player(2l,"Player 2");
            when(playerDao.getPlayer(2L)).thenReturn(playerTwo);

            Player playerThree = new Player(3l,"Player 3");
            when(playerDao.getPlayer(3L)).thenReturn(playerThree);

            Player playerFour = new Player(4l,"Player 4");
            when(playerDao.getPlayer(4L)).thenReturn(playerFour);

            eloRankingService = new EloRankingServiceImpl(matchDao,playerDao,eloRatingService);
            List<Player> players = eloRankingService.getRanking();

            assertNotNull(players);
            assertEquals(4,players.size());

            assertEquals(playerOne, players.get(0));
            assertEquals(0, players.get(0).getLostMatches());
            assertEquals(3, players.get(0).getWonMatches());

            assertEquals(playerTwo, players.get(1));
            assertEquals(playerThree, players.get(2));
            assertEquals(playerFour, players.get(3));

        } catch (ApplicationException e) {
            fail();
        }
    }


    @Test
    public void testGetResultsShouldBringAllMatchesPlayedByPlayer() {
        long playerId=2L;

        eloRankingService = new EloRankingServiceImpl(matchDao,playerDao,null);

        try {
            List<Match> matchesPlayedByPlayer = new ArrayList<Match>();
            matchesPlayedByPlayer.add(matches.get(0));
            matchesPlayedByPlayer.add(matches.get(3));
            matchesPlayedByPlayer.add(matches.get(4));
            when(matchDao.getMatchesPlayedBy(playerId)).thenReturn(matchesPlayedByPlayer);

            List<MatchResultForPlayer> matchResultForPlayer = eloRankingService.getResults(playerId);

            assertEquals(3,matchResultForPlayer.size());
            Assert.assertEquals(MatchResult.LOST, matchResultForPlayer.get(0).getMatchResult());
            Assert.assertEquals(MatchResult.WON, matchResultForPlayer.get(1).getMatchResult());
            Assert.assertEquals(MatchResult.WON, matchResultForPlayer.get(2).getMatchResult());
            assertEquals(1,matchResultForPlayer.get(0).getOpponentId());
            assertEquals(3,matchResultForPlayer.get(1).getOpponentId());
            assertEquals(4,matchResultForPlayer.get(2).getOpponentId());
        } catch (ApplicationException e) {
            fail();
        }
    }


    @Test
    public void getResultsShouldFailWhenNonExistentPlayerIdIsSent(){
        Long playerId = 180l;

        try {
            when(playerDao.getPlayer(playerId)).thenThrow(new ApplicationException("Error"));
        } catch (ApplicationException e) {
            fail();
        }

        eloRankingService = new EloRankingServiceImpl(null,playerDao,null);

        try {
            eloRankingService.getResults(playerId);
            fail();
        } catch (ApplicationException e) {
            assertNotNull(e);
            assertNotNull(e.getMessage());
            assertEquals("Error", e.getMessage());
        }
    }


    @Test
    public void getSuggestedNewMatches(){
        eloRankingService = new EloRankingServiceImpl(matchDao,playerDao,eloRatingService);

        try {
            createPlayers();

            List<Match> newMatches = createNewMatches();
            when(matchDao.getAll()).thenReturn(newMatches);

            List<Match> suggestedMatches = eloRankingService.getSuggestedNewMatches();

            assertNotNull(suggestedMatches);
            assertEquals(1, suggestedMatches.size());
            assertEquals(1, suggestedMatches.get(0).getPlayerOneId());
            assertEquals(2, suggestedMatches.get(0).getPlayerTwoId());

        } catch (ApplicationException e) {
            fail();
        }
    }


    @Test
    public void getSuggestedNewMatchesShouldReturnEmptyListWhenNoFutureMatchIsPossible(){
        eloRankingService = new EloRankingServiceImpl(matchDao,playerDao,eloRatingService);

        try {
            createPlayers();
            List<Match> suggestedMatches = eloRankingService.getSuggestedNewMatches();

            assertNotNull(suggestedMatches);
            assertEquals(0, suggestedMatches.size());

        } catch (ApplicationException e) {
            fail();
        }
    }


    private void createPlayers() throws ApplicationException {
        Player playerOne = new Player(1l,"Player 1");
        when(playerDao.getPlayer(1L)).thenReturn(playerOne);

        Player playerTwo = new Player(2l,"Player 2");
        when(playerDao.getPlayer(2L)).thenReturn(playerTwo);

        Player playerThree = new Player(3l,"Player 3");
        when(playerDao.getPlayer(3L)).thenReturn(playerThree);

        Player playerFour = new Player(4l,"Player 4");
        when(playerDao.getPlayer(4L)).thenReturn(playerFour);
    }

    private List<Match> createNewMatches() {
        List<Match> matches = new ArrayList<Match>();

        Match match = new Match(1l,2l, MatchResult.DRAW);
        matches.add(match);
        Match match2 = new Match(1l,3l,MatchResult.WON);
        matches.add(match2);
        Match match3 = new Match(1l,4l,MatchResult.WON);
        matches.add(match3);
        Match match4 = new Match(2l,3l,MatchResult.WON);
        matches.add(match4);
        Match match5 = new Match(2l,4l,MatchResult.WON);
        matches.add(match5);
        Match match6 = new Match(4l,3l,MatchResult.WON);
        matches.add(match6);

        return matches;
    }


    private List<Match> createMatches() {
        List<Match> matches = new ArrayList<Match>();

        Match match = new Match(1l,2l, MatchResult.WON);
        matches.add(match);
        Match match2 = new Match(1l,3l,MatchResult.WON);
        matches.add(match2);
        Match match3 = new Match(1l,4l,MatchResult.WON);
        matches.add(match3);
        Match match4 = new Match(2l,3l,MatchResult.WON);
        matches.add(match4);
        Match match5 = new Match(2l,4l,MatchResult.WON);
        matches.add(match5);
        Match match6 = new Match(3l,4l,MatchResult.WON);
        matches.add(match6);

        return matches;
    }


}