package com.mycompany.gameRankings.daos.impl;

import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.daos.PlayerDao;
import com.mycompany.gameRankings.exceptions.ApplicationException;
import org.junit.Before;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class PlayerDaoFileReaderImplTest {

    private String fileLocation = "src/main/resources" + File.separator + "names.txt";
    private PlayerDao playerDao;

    @Before
    public void setup(){
        playerDao = new PlayerDaoFileReaderImpl(fileLocation);
    }


    @Test
    public void testGetAllReturnsAllPlayersSuccessfully(){
        Player player = new Player(24l,"Jeanine");

        try {
            List<Player> players = playerDao.getAll();
            assertNotNull(players);
            assertEquals(40, players.size());

            assertTrue(players.contains(player));

        } catch (ApplicationException e) {
            fail();
        }
    }


    @Test
    public void testGetPlayerCannotFindPlayer(){
        long playerId = 1800l;

        try {
            playerDao.getPlayer(playerId);
            fail();
        } catch (ApplicationException e) {
            assertNotNull(e);
            assertNotNull(e.getMessage());
        }
    }


}