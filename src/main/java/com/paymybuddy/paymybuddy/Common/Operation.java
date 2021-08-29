package com.paymybuddy.paymybuddy.Common;

public enum  Operation {
    IN("in"),
    OUT("out");

    private String type;

    Operation(String type){
        this.type = type;
    }
}
