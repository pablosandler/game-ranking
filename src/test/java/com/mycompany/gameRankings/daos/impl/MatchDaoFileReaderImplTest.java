package com.mycompany.gameRankings.daos.impl;

import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.daos.MatchDao;
import com.mycompany.gameRankings.enums.MatchResult;
import com.mycompany.gameRankings.exceptions.ApplicationException;
import org.junit.Test;

import java.io.File;
import java.util.List;

import static org.junit.Assert.*;

public class MatchDaoFileReaderImplTest {

    private String fileLocation = "src/main/resources" + File.separator + "matches.txt";

    @Test
    public void testGetAllReturnsAllMatchesSuccessfully(){
        MatchDao matchDaoFileReader = new MatchDaoFileReaderImpl(fileLocation);
        Match match = new Match(21,7, MatchResult.WON);

        try {
            List<Match> matches = matchDaoFileReader.getAll();
            assertNotNull(matches);
            assertEquals(100, matches.size());

            assertTrue(matches.contains(match));

        } catch (ApplicationException e) {
            fail();
        }
    }


    @Test
    public void testGetMatchesPlayedByPlayer(){
        long playerId = 1L;
        MatchDao matchDaoFileReader = new MatchDaoFileReaderImpl(fileLocation);

        try {
            List<Match> matches = matchDaoFileReader.getMatchesPlayedBy(playerId);

            assertNotNull(matches);
            assertEquals(6, matches.size());
        } catch (ApplicationException e) {
            fail();
        }
    }


}