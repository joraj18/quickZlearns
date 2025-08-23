package com.jo.quickZlearn.auth.exceptions;

public class InvalidCredentialsException extends RuntimeException{
    public InvalidCredentialsException(String msg){
        super(msg);
    }
}
