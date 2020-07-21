package com.joeypine.accounting.exception;

public enum BizErrorCode {
    INVALID_PARAMETER("INVALID_PARAMETER"),
    Resourse_NOT_FOUNDE("Resourse_NOT_FOUNDE"),
    NO_UNAUTHORIZED("NO_UNAUTHORIZED"),
    INCORRECT_CREDENTIALS("INCORRECT_CREDENTIALS");;

    private String message;

    BizErrorCode(String message) {
        this.message = message;
    }
}
