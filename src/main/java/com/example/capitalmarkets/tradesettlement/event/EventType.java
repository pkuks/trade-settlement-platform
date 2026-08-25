package com.example.capitalmarkets.tradesettlement.audit;

public enum AuditEventType {
    TRADE_CREATED,
    TRADE_VALIDATED,
    TRADE_READY_FOR_SETTLEMENT,

    SETTLEMENT_CREATED,
    SETTLEMENT_PROCESSING,
    SETTLEMENT_SETTLED,
    SETTLEMENT_FAILED,
    SETTLEMENT_RETRIED
}
