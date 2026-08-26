package com.example.capitalmarkets.tradesettlement.event;

import lombok.Builder;

import java.time.Instant;
import java.util.UUID;

@Builder
public record SettlementEvent(
        UUID eventId,
        EventType eventType,
        UUID settlementId,
        UUID tradeId,
        String settlementReference,
        String username,
        String reason,
        Integer retryCount,
        Instant eventTime
) {
}
