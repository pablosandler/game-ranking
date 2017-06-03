package com.mycompany.gameRankings.userInterface.enums;


public enum Command {
    GET_RANK("rank"),
    GET_RANKING("ranking"),
    GET_RESULTS("results"),
    GET_SUGGESTED_NEW_MATCHES("new_matches");

    private String name;

    private Command(String name) {
        this.name = name;
    }


    public static Command getCommand(String commandInput) {
        if(commandInput.equals(Command.GET_RANK.name)){
            return Command.GET_RANK;
        }

        if(commandInput.equals(Command.GET_RANKING.name)){
            return Command.GET_RANKING;
        }

        if(commandInput.equals(Command.GET_RESULTS.name)){
            return Command.GET_RESULTS;
        }

        if(commandInput.equals(Command.GET_SUGGESTED_NEW_MATCHES.name)){
            return Command.GET_SUGGESTED_NEW_MATCHES;
        }

        return null;
    }


}
