package com.example.playtox.exception;

import java.util.function.Supplier;

public class NotFoundAccount extends RuntimeException{
    public NotFoundAccount(String message) {
        super(message);
    }

    public NotFoundAccount() {

    }
}
