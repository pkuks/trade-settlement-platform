package com.example.capitalmarkets.tradesettlement.settlement;

import jakarta.validation.constraints.NotBlank;

public record FailSettlementRequest(
        @NotBlank
        String reason
) {
}
