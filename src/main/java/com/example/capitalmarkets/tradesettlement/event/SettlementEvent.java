package com.example.capitalmarkets.tradesettlement.event;

import java.util.UUID;

public record SettlementEvent(
        EventType eventType,
        UUID settlementId,
        UUID tradeId,
        String settlementReference,
        String username,
        String reason,
        Integer retryCount
) {
}
