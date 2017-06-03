package com.mycompany.gameRankings.userInterface;

import com.mycompany.gameRankings.beans.Match;
import com.mycompany.gameRankings.beans.MatchResultForPlayer;
import com.mycompany.gameRankings.beans.Player;
import com.mycompany.gameRankings.daos.MatchDao;
import com.mycompany.gameRankings.daos.PlayerDao;
import com.mycompany.gameRankings.daos.impl.MatchDaoFileReaderImpl;
import com.mycompany.gameRankings.daos.impl.PlayerDaoFileReaderImpl;
import com.mycompany.gameRankings.exceptions.ApplicationException;
import com.mycompany.gameRankings.services.EloRankingService;
import com.mycompany.gameRankings.services.impl.EloRankingServiceImpl;
import com.mycompany.gameRankings.services.impl.EloRatingServiceImpl;
import com.mycompany.gameRankings.userInterface.enums.Command;
import com.mycompany.gameRankings.userInterface.output.OutputWriter;
import com.mycompany.gameRankings.userInterface.output.impl.OutputWriterCommandLineImpl;

import java.io.File;
import java.util.List;

public class GameRankings {

    public static final int NAMES_FILE_PARAMETER_INDEX = 0;
    public static final int MATCHES_FILE_PARAMETER_INDEX = 1;
    public static final int PLAYER_ID_PARAMETER_INDEX = 3;
    public static final int COMMAND_PARAMETER_INDEX = 2;
    public static final int MIN_NUMBER_OF_PARAMETERS = 3;


    public static void main(String[] args) {

        try{
            validateParameters(args);
        } catch (IllegalArgumentException e){
            System.err.println(e.getMessage());
            return;
        }

        Command command = Command.getCommand(args[COMMAND_PARAMETER_INDEX]);
        long playerId = getPlayerId(args, command);

        String namesFile = args[NAMES_FILE_PARAMETER_INDEX];
        String matchesFile = args[MATCHES_FILE_PARAMETER_INDEX];

        EloRankingService eloRankingService = getEloRankingService(namesFile, matchesFile);

        OutputWriter outputWriter = new OutputWriterCommandLineImpl();

        try {
            executeCommand(command, playerId, eloRankingService, outputWriter);
        } catch (ApplicationException e) {
            System.err.println(e.getMessage());
        }

    }

    private static void executeCommand(Command command, long playerId, EloRankingService eloRankingService, OutputWriter outputWriter) throws ApplicationException {
        switch (command){
            case GET_RANK:
                int rank = eloRankingService.getRank(playerId);
                outputWriter.outputRank(playerId,rank);
                break;
            case GET_RESULTS:
                List<MatchResultForPlayer> matchResultForPlayers = eloRankingService.getResults(playerId);
                outputWriter.outputResultsForPlayer(playerId, matchResultForPlayers);
                break;
            case GET_RANKING:
                List<Player> players = eloRankingService.getRanking();
                outputWriter.outputRanking(players);
                break;
            case GET_SUGGESTED_NEW_MATCHES:
                List<Match> matches = eloRankingService.getSuggestedNewMatches();
                outputWriter.outputSuggestedNewMatches(matches);
                break;
        }
    }


    private static long getPlayerId(String[] args, Command command) {
        long playerId = 0;
        if(Command.GET_RANK.equals(command) || Command.GET_RESULTS.equals(command)){
            playerId = Long.parseLong(args[PLAYER_ID_PARAMETER_INDEX]);
        }
        return playerId;
    }


    private static EloRankingService getEloRankingService(String namesFile, String matchesFile) {
        MatchDao matchDao = new MatchDaoFileReaderImpl(matchesFile);
        PlayerDao playerDao = new PlayerDaoFileReaderImpl(namesFile);

        return new EloRankingServiceImpl(matchDao,playerDao,new EloRatingServiceImpl());
    }


    private static void validateParameters(String[] args) throws IllegalArgumentException {
        if(args.length<MIN_NUMBER_OF_PARAMETERS){
            throw new IllegalArgumentException("Wrong number of paraters. They should be: nameFile matchesFile command <parameter for command>");
        }

        String commandInput = args[COMMAND_PARAMETER_INDEX];
        Command command = Command.getCommand(commandInput);

        if(null==command){
            throw new IllegalArgumentException("Couldn't find command. These are the commands available: rank, ranking, results, new_matches");
        }

        if( (command.equals(Command.GET_RESULTS) || command.equals(Command.GET_RANK)) && args.length==MIN_NUMBER_OF_PARAMETERS){
            throw new IllegalArgumentException("Parameter for command missing");
        }

        if((command.equals(Command.GET_RESULTS) || command.equals(Command.GET_RANK))){
            try{
                Long.parseLong(args[PLAYER_ID_PARAMETER_INDEX]);
            } catch(NumberFormatException e){
                throw new IllegalArgumentException("Parameter for command should be an integer");
            }

        }

        validateFiles(args);
    }


    private static void validateFiles(String[] args) throws IllegalArgumentException {
        String namesFile = args[NAMES_FILE_PARAMETER_INDEX];
        File file = new File(namesFile);
        if(!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("Cannot find file "+ namesFile + " in the current directory");
        }

        String matchesFile = args[MATCHES_FILE_PARAMETER_INDEX];
        file = new File(matchesFile);
        if(!file.exists() || file.isDirectory()) {
            throw new IllegalArgumentException("Cannot find file "+ matchesFile + " in the current directory");
        }
    }


}
