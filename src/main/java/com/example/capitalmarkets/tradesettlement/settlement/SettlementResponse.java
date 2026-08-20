package com.example.capitalmarkets.tradesettlement.settlement;

import java.util.UUID;

public record SettlementResponse(
        UUID id,
        String settlementReference,
        String tradeReference,
        SettlementStatus status
) {
}
