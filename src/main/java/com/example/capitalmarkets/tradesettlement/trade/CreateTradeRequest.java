package com.example.capitalmarkets.tradesettlement.trade;

import jakarta.validation.constraints.DecimalMin;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Size;

import java.math.BigDecimal;
import java.time.LocalDate;

public record CreateTradeRequest(
        @NotBlank
        String tradeReference,

        @NotNull
        String tradeType,

        @NotBlank
        String securityId,

        @NotNull
        @DecimalMin("0.0001")
        BigDecimal quantity,

        @NotNull
        @DecimalMin("0.0001")
        BigDecimal price,

        @NotBlank
        @Size(min=3, max=3)
        String currency,

        @NotNull
        LocalDate tradeDate,

        @NotNull
        LocalDate settlementDate

) {
}
