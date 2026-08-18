package com.example.capitalmarkets.tradesettlement.common.exception;

public class InvalidTradeSettlementDateException extends RuntimeException{
    public InvalidTradeSettlementDateException() {
    }

    public InvalidTradeSettlementDateException(String message) {
        super(message);
    }
}
