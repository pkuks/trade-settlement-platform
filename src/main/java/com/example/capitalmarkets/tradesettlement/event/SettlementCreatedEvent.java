package com.example.capitalmarkets.tradesettlement.event;

import java.util.UUID;

public record SettlementCreatedEvent(
        UUID settlementId,
        UUID tradeId,
        String settlementReference,
        String username
) {
}
