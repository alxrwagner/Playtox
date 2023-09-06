package com.example.playtox.exception;

public class NotFoundAccount extends RuntimeException{
    public NotFoundAccount(String message) {
        super(message);
    }
}
