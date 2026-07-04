package com.example.offerops.exception;

public class UserAlreadyExist extends  RuntimeException{

    public UserAlreadyExist(String message){
        super(message);
    }

}
