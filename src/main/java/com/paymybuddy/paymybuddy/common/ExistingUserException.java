package com.paymybuddy.paymybuddy.common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExistingUserException extends RuntimeException{

    public ExistingUserException(String message) {
        super(message);
    }
}
