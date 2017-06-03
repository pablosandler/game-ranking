package com.mycompany.gameRankings.exceptions;

public class ApplicationException extends Exception {

    public ApplicationException(String message, Throwable e){
        super(message,e);
    }

    public ApplicationException(String message){
        super(message);
    }

}
