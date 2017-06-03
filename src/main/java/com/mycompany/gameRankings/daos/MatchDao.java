package com.mycompany.gameRankings.daos;

import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.exceptions.ApplicationException;

import java.util.List;


public interface MatchDao extends GenericDao<Match> {

    List<Match> getMatchesPlayedBy(long playerId) throws ApplicationException;

}
