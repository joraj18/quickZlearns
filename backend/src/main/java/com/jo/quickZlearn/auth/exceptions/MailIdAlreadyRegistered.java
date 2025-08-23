package com.jo.quickZlearn.auth.exceptions;


public class MailIdAlreadyRegistered extends RuntimeException{
    public MailIdAlreadyRegistered(String msg){
        super(msg);
    }
}
