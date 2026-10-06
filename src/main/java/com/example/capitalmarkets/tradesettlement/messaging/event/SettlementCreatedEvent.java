package com.example.capitalmarkets.tradesettlement.messaging.event;

import java.util.UUID;

public record SettlementCreatedEvent(
        UUID settlementId,
        UUID tradeId,
        String settlementReference,
        String username
) {
}
