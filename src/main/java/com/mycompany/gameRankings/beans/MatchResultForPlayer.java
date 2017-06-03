package com.mycompany.gameRankings.beans;


import com.google.common.base.Objects;
import com.mycompany.gameRankings.enums.MatchResult;

public class MatchResultForPlayer {

    private long opponentId;
    private MatchResult matchResult;

    public MatchResultForPlayer(long opponentId, MatchResult matchResult) {
        this.opponentId = opponentId;
        this.matchResult = matchResult;
    }

    public long getOpponentId() {
        return opponentId;
    }

    public MatchResult getMatchResult() {
        return matchResult;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof MatchResultForPlayer)) return false;
        MatchResultForPlayer that = (MatchResultForPlayer) o;
        return Objects.equal(getOpponentId(), that.getOpponentId()) &&
                Objects.equal(getMatchResult(), that.getMatchResult());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getOpponentId(), getMatchResult());
    }

}
