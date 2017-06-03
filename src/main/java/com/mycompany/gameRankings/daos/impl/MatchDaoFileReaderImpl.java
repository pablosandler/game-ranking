package com.mycompany.gameRankings.daos.impl;


import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.daos.MatchDao;
import com.mycompany.gameRankings.enums.MatchResult;
import com.mycompany.gameRankings.exceptions.ApplicationException;

import java.util.ArrayList;
import java.util.List;

public class MatchDaoFileReaderImpl extends AbstractDaoFileReader<Match> implements MatchDao {

    private String fileLocation;

    public MatchDaoFileReaderImpl(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    protected String getFileLocation() {
        return fileLocation;
    }

    @Override
    protected Match buildObject(String[] data) {
        Long playerOne = Long.valueOf(data[0]);
        Long playerTwo = Long.valueOf(data[1]);

        Match match = new Match(playerOne,playerTwo, MatchResult.WON);
        return match;
    }

    public List<Match> getMatchesPlayedBy(long playerId) throws ApplicationException {
        List<Match> matches = getAll();
        List<Match> matchesPlayed = new ArrayList<Match>();

        for(Match match : matches){
            if(playerId==match.getPlayerOneId() || playerId==match.getPlayerTwoId()){
                matchesPlayed.add(match);
            }
        }
        return matchesPlayed;
    }


}
