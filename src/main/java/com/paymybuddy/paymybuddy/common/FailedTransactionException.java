package com.paymybuddy.paymybuddy.common;

public class FailedTransactionException extends RuntimeException{

    public FailedTransactionException(String message){
        super(message);
    }
}
