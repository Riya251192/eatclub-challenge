package com.eatclub.exception;

import org.springframework.http.HttpStatusCode;

public class ServiceException extends RuntimeException{
    public ServiceException(String msg , Throwable throwable){
        super(msg , throwable);
    }
    public ServiceException(String msg){
        super(msg);
    }

}
