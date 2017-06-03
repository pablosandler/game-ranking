package com.mycompany.gameRankings.beans;


import com.google.common.base.Objects;
import com.mycompany.gameRankings.enums.MatchResult;

public class Match {

    private long playerOneId;
    private long playerTwoId;
    private MatchResult matchResultForPlayerOne;


    public Match(long playerOneId, long playerTwoId, MatchResult matchResultForPlayerOne) {
        this.playerOneId = playerOneId;
        this.playerTwoId = playerTwoId;
        this.matchResultForPlayerOne = matchResultForPlayerOne;
    }

    public long getPlayerOneId() {
        return playerOneId;
    }

    public long getPlayerTwoId() {
        return playerTwoId;
    }


    public MatchResult getMatchResultForPlayerOne() {
        return matchResultForPlayerOne;
    }

    public MatchResult getMatchResultForPlayerTwo() {
        if(getMatchResultForPlayerOne().equals(MatchResult.DRAW)){
            return MatchResult.DRAW;
        }

        if(getMatchResultForPlayerOne().equals(MatchResult.WON)){
            return MatchResult.LOST;
        }

        return MatchResult.WON;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Match)) return false;
        Match match = (Match) o;
        return Objects.equal(getPlayerOneId(), match.getPlayerOneId()) &&
                Objects.equal(getPlayerTwoId(), match.getPlayerTwoId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getPlayerOneId(), getPlayerTwoId());
    }
}
