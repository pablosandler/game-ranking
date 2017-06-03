package com.mycompany.gameRankings.beans;

import com.google.common.base.Objects;
import com.mycompany.gameRankings.enums.MatchResult;

public class Player {

    private Long id;
    private String name;
    private int wonMatches;
    private int lostMatches;
    private int rating;

    public Player(Long id,String name) {
        this.id = id;
        this.name = name;
        this.wonMatches = 0;
        this.lostMatches = 0;
        this.rating = 0;
    }

    public String getName() {
        return name;
    }

    public Long getId() {
        return id;
    }

    public int getWonMatches() {
        return wonMatches;
    }

    public int getLostMatches() {
        return lostMatches;
    }

    public int getRating() {
        return rating;
    }

    public void setRating(int rating) {
        this.rating = rating;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (!(o instanceof Player)) return false;
        Player player = (Player) o;
        return Objects.equal(getName(), player.getName()) &&
                Objects.equal(getId(), player.getId());
    }

    @Override
    public int hashCode() {
        return Objects.hashCode(getId(),getName());
    }


    public void addPlayedMatch(MatchResult matchResult){
        if(matchResult.equals(MatchResult.LOST)){
            lostMatches++;
        } else {
            wonMatches++;
        }
    }

}
