package com.mycompany.gameRankings.daos.impl;


import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.daos.PlayerDao;
import com.mycompany.gameRankings.exceptions.ApplicationException;
import org.apache.commons.collections.CollectionUtils;
import org.apache.commons.collections.Predicate;

import java.util.List;

public class PlayerDaoFileReaderImpl extends AbstractDaoFileReader<Player> implements PlayerDao {

    private String fileLocation;

    public PlayerDaoFileReaderImpl(String fileLocation) {
        this.fileLocation = fileLocation;
    }

    @Override
    protected String getFileLocation() {
        return fileLocation;
    }

    @Override
    protected Player buildObject(String[] data) {
        Long playerId = Long.valueOf(data[0]);
        String name = data[1];

        Player player = new Player(playerId,name);
        return player;
    }

    public Player getPlayer(final long playerId) throws ApplicationException {
        List<Player> players = getAll();

        Player player = (Player)CollectionUtils.find(players, new Predicate() {
            public boolean evaluate(Object o) {
                return ((Player)o).getId().equals(playerId);
            }
        });

        if(null==player){
            throw new ApplicationException("Player not found");
        }

        return player;
    }

}
