package com.mycompany.gameRankings.userInterface.enums;

import org.junit.Test;

import static org.junit.Assert.assertEquals;
import static org.junit.Assert.assertNull;

public class CommandTest {

    @Test
    public void getCommandShouldReturnProperCommandEnumOnCorrectString(){
        Command command = Command.getCommand("rank");
        assertEquals(Command.GET_RANK,command);

        command = Command.getCommand("ranking");
        assertEquals(Command.GET_RANKING,command);

        command = Command.getCommand("results");
        assertEquals(Command.GET_RESULTS,command);

        command = Command.getCommand("new_matches");
        assertEquals(Command.GET_SUGGESTED_NEW_MATCHES,command);
    }

    @Test
    public void getCommandShouldReturnNullOnIncorrectString(){
        Command command = Command.getCommand("x");
        assertNull(command);
    }

}