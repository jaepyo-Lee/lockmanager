package com.ime.lockmanager.common.response.exception;

public class TokenValidFailedException extends RuntimeException{
    public TokenValidFailedException(){
        super("Failed to generate Token.");
    }
    public TokenValidFailedException(String message){
        super(message);
    }
}
