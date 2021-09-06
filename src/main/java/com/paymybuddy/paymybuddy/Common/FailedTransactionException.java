package com.paymybuddy.paymybuddy.Common;

public class FailedTransactionException extends RuntimeException{

    public FailedTransactionException(String message){
        super(message);
    }
}
