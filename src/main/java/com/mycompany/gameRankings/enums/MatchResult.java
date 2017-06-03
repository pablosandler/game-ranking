package com.mycompany.gameRankings.enums;


public enum MatchResult {

    WON(1.0),
    DRAW(0.5),
    LOST(0.0);

    private double value;

    private MatchResult(double value) {
        this.value = value;
    }

    public double getValue() {
        return value;
    }

}
