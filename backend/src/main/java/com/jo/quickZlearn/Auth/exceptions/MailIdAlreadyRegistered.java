package com.jo.quickZlearn.Auth.exceptions;


public class MailIdAlreadyRegistered extends RuntimeException{
    public MailIdAlreadyRegistered(String msg){
        super(msg);
    }
}
