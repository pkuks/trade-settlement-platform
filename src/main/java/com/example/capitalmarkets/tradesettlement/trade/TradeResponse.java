package com.example.capitalmarkets.tradesettlement.trade;


import java.math.BigDecimal;
import java.time.LocalDate;
import java.util.UUID;

public record TradeResponse(
        UUID id,
        String tradeReference,
        TradeType tradeType,
        String securityId,
        BigDecimal quantity,
        BigDecimal price,
        String currency,
        LocalDate tradeDate,
        LocalDate settlementDate,
        TradeStatus status,
        String createdBy
        ) {
}
