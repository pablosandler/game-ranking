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
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.*;

public class EloRankingServiceImpl implements EloRankingService {

    public static final double THRESHOLD = 0.01;
    private MatchDao matchDao;
    private PlayerDao playerDao;
    private EloRatingService eloRatingService;

    public EloRankingServiceImpl(MatchDao matchDao, PlayerDao playerDao, EloRatingService eloRatingService) {
        this.matchDao = matchDao;
        this.playerDao = playerDao;
        this.eloRatingService = eloRatingService;
    }


    public int getRank(long playerId) throws ApplicationException {
        Map<Long,Integer> ratingByPlayer = new HashMap<Long, Integer>();

        playerDao.getPlayer(playerId);

        List<Match> matches = matchDao.getAll();
        for(Match match : matches){

            Long playerOneId = match.getPlayerOneId();
            Long playerTwoId = match.getPlayerTwoId();

            Integer currentRating = getCurrentRating(ratingByPlayer.get(playerOneId));
            Integer opponentRating = getCurrentRating(ratingByPlayer.get(playerTwoId));

            int newRating = eloRatingService.getNewRating(currentRating, opponentRating, match.getMatchResultForPlayerOne());
            ratingByPlayer.put(playerOneId,newRating);

            newRating = eloRatingService.getNewRating(opponentRating, currentRating, match.getMatchResultForPlayerTwo());
            ratingByPlayer.put(playerTwoId,newRating);
        }

        return ratingByPlayer.get(playerId);
    }


    private Integer getCurrentRating(Integer rating) {
        if(rating==null){
            return 0;
        }
        return rating;
    }


    public List<Player> getRanking() throws ApplicationException {
        List<Player> players = new ArrayList<Player>();

        List<Match> matches = matchDao.getAll();

        for(Match match : matches){
            Player playerOne = getPlayer(players, match.getPlayerOneId());
            Player playerTwo = getPlayer(players, match.getPlayerTwoId());

            Integer currentRating = playerOne.getRating();
            Integer opponentRating = playerTwo.getRating();

            int newRating = eloRatingService.getNewRating(currentRating, opponentRating, match.getMatchResultForPlayerOne());
            playerOne.setRating(newRating);
            playerOne.addPlayedMatch(match.getMatchResultForPlayerOne());

            newRating = eloRatingService.getNewRating(opponentRating, currentRating, match.getMatchResultForPlayerTwo());
            playerTwo.setRating(newRating);
            playerTwo.addPlayedMatch(match.getMatchResultForPlayerTwo());
        }

        Collections.sort(players, new Comparator<Player>() {
            public int compare(Player playerOne, Player playerTwo) {
                return playerTwo.getRating()-playerOne.getRating();
            }
        });

        return players;
    }


    private Player getPlayer(List<Player> players, final long playerId) throws ApplicationException {
        Player player = (Player) CollectionUtils.find(players, new Predicate() {
            public boolean evaluate(Object o) {
                return ((Player) o).getId().equals(playerId);
            }
        });

        if(null==player){
            player = playerDao.getPlayer(playerId);
            players.add(player);
        }

        return player;
    }


    public List<MatchResultForPlayer> getResults(long playerId) throws ApplicationException {
        playerDao.getPlayer(playerId);

        List<Match> matches = matchDao.getMatchesPlayedBy(playerId);

        List<MatchResultForPlayer> matchResultForPlayers = new ArrayList<MatchResultForPlayer>();

        for(Match match : matches){
            long opponentId;
            MatchResult matchResult;

            if(playerId==match.getPlayerTwoId()){
                opponentId = match.getPlayerOneId();
                matchResult = match.getMatchResultForPlayerTwo();
            } else {
                opponentId = match.getPlayerTwoId();
                matchResult = match.getMatchResultForPlayerOne();
            }


            MatchResultForPlayer matchResultForPlayer = new MatchResultForPlayer(opponentId,matchResult);
            matchResultForPlayers.add(matchResultForPlayer);

        }

        return matchResultForPlayers;
    }


    public List<Match> getSuggestedNewMatches() throws ApplicationException {
        final List<Player> playersAlreadyProcessed = new ArrayList<Player>();
        List<Match> matches = new ArrayList<Match>();

        List<Player> players = getRanking();

        for(Player player : players){
            playersAlreadyProcessed.add(player);

            final int playerRating = player.getRating();
            final long playerId = player.getId();

            Collection playersToPlayAgainst = CollectionUtils.select(players, getExpectedScorePredicate(playersAlreadyProcessed, playerRating, playerId));

            for(Object playerToPlayAgainst : playersToPlayAgainst){
                Match match = new Match(player.getId(),((Player)playerToPlayAgainst).getId(),MatchResult.DRAW);
                matches.add(match);
            }

        }

        return matches;
    }

    private Predicate getExpectedScorePredicate(final List<Player> playersAlreadyProcessed, final int playerRating, final long playerId) {
        return new Predicate() {
            public boolean evaluate(Object o) {
                Player player = (Player) o;

                if(playerId==player.getId() || playersAlreadyProcessed.contains(o)){
                    return false;
                }

                double expectedScore = eloRatingService.calculateExpectedScore(playerRating, player.getRating());
                return Math.abs(0.5 - expectedScore)< THRESHOLD;
            }
        };
    }

}
