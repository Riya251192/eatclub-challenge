package com.eatclub.exception;

public class ApplicationException extends RuntimeException{
    public ApplicationException(String msg , Throwable throwable){
        super(msg , throwable);
    }
    public ApplicationException(String msg){
        super(msg);
    }

}