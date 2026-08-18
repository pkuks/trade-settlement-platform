package com.example.capitalmarkets.tradesettlement.common.exception;

public class TradeAlreadyExistsException extends RuntimeException {
    public TradeAlreadyExistsException() {
    }

    public TradeAlreadyExistsException(String message) {
        super(message);
    }
}
