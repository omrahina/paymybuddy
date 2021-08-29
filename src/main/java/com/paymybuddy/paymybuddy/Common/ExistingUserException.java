package com.paymybuddy.paymybuddy.Common;

import lombok.NoArgsConstructor;

@NoArgsConstructor
public class ExistingUserException extends RuntimeException{

    public ExistingUserException(String message) {
        super(message);
    }
}
