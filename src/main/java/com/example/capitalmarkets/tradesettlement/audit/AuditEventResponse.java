package com.example.capitalmarkets.tradesettlement.audit;

import java.time.LocalDateTime;

public record AuditEventResponse(
        String eventType,
        String username,
        LocalDateTime eventTime,
        String details
) {
}
