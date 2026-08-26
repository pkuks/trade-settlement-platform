package com.example.capitalmarkets.tradesettlement.event;

import lombok.Builder;
import java.util.UUID;

@Builder
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
