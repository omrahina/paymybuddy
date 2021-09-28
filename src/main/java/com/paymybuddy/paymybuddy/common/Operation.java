package com.paymybuddy.paymybuddy.common;

public enum  Operation {
    IN("in"),
    OUT("out");

    private String type;

    Operation(String type){
        this.type = type;
    }
}
