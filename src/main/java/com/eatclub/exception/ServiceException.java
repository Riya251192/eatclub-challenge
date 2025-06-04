package com.eatclub.exception;

public class ServiceException extends RuntimeException{
    public ServiceException(String msg , Throwable throwable){
        super(msg , throwable);
    }
    public ServiceException(String msg){
        super(msg);
    }

}
