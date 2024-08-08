package com.example.assessment.exception;

public class SetNameNotFoundException extends RuntimeException {

    public SetNameNotFoundException(String setName){
        super("Set name not found: " + setName);
    }
}

