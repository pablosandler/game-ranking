package com.mycompany.gameRankings.services;

import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.beans.MatchResultForPlayer;
import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.exceptions.ApplicationException;

import java.util.List;


public interface EloRankingService {

    int getRank(long playerId) throws ApplicationException;

    List<Player> getRanking() throws ApplicationException;

    List<MatchResultForPlayer> getResults(long playerId) throws ApplicationException;

    List<Match> getSuggestedNewMatches() throws ApplicationException;

}
