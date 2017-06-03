package com.mycompany.gameRankings.daos;

import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.exceptions.ApplicationException;

public interface PlayerDao extends GenericDao<Player>{

    Player getPlayer(long playerId) throws ApplicationException;

}
