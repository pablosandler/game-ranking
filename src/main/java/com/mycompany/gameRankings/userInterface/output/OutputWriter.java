package com.mycompany.gameRankings.userInterface.output;


import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.beans.MatchResultForPlayer;
import com.mycompany.gameRankings.beans.Player;

import java.util.List;

public interface OutputWriter {

    void outputRank(long playerId, int rank);

    void outputResultsForPlayer(long playerId, List<MatchResultForPlayer> matchResultsForPlayer);

    void outputRanking(List<Player> players);

    void outputSuggestedNewMatches(List<Match> matches);
}
