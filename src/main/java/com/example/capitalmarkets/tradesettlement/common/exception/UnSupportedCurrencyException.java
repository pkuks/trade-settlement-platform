package com.example.capitalmarkets.tradesettlement.common.exception;

public class UnSupportedCurrencyException extends RuntimeException{

    public UnSupportedCurrencyException() {
    }

    public UnSupportedCurrencyException(String message) {
        super(message);
    }
}
