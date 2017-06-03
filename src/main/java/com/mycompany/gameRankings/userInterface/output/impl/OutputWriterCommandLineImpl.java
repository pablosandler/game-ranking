package com.mycompany.gameRankings.userInterface.output.impl;

import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.beans.MatchResultForPlayer;
import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.userInterface.output.OutputWriter;

import java.util.List;

public class OutputWriterCommandLineImpl implements OutputWriter {


    public void outputRank(long playerId, int rank) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Rank for player ");
        stringBuilder.append(playerId);
        stringBuilder.append(" : ");
        stringBuilder.append(rank);
        System.out.print(stringBuilder.toString());
    }


    public void outputResultsForPlayer(long playerId, List<MatchResultForPlayer> matchResultsForPlayer) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Results for player ");
        stringBuilder.append(playerId);
        stringBuilder.append(" : \n");


        for(MatchResultForPlayer matchResultForPlayer : matchResultsForPlayer){
            stringBuilder.append("Opponent: ");
            stringBuilder.append(matchResultForPlayer.getOpponentId());
            stringBuilder.append("      Result: ");
            stringBuilder.append(matchResultForPlayer.getMatchResult());
            stringBuilder.append("\n");
        }

        System.out.print(stringBuilder.toString());
    }


    public void outputRanking(List<Player> players) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Ranking : \n");

        int position = 1;
        for(Player player : players){
            stringBuilder.append(position++);
            stringBuilder.append("  Name: ");
            stringBuilder.append(player.getName());
            stringBuilder.append("  Wins: ");
            stringBuilder.append(player.getWonMatches());
            stringBuilder.append("  Losses: ");
            stringBuilder.append(player.getLostMatches());
            stringBuilder.append("  Score: ");
            stringBuilder.append(player.getRating());
            stringBuilder.append("\n");
        }

        System.out.print(stringBuilder.toString());
    }


    public void outputSuggestedNewMatches(List<Match> matches) {
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("Suggested matches : \n");

        for(Match match : matches){
            stringBuilder.append(match.getPlayerOneId());
            stringBuilder.append("  vs. ");
            stringBuilder.append(match.getPlayerTwoId());
            stringBuilder.append("\n");
        }

        System.out.print(stringBuilder.toString());
    }


}
