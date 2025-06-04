package com.eatclub.exception;

public class BadRequestException extends RuntimeException{
    public BadRequestException(String msg , Throwable throwable){
        super(msg , throwable);
    }
    public BadRequestException(String msg){
        super(msg);
    }

}
