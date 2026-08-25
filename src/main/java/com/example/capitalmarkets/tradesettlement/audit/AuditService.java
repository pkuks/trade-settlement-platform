package com.example.capitalmarkets.tradesettlement.audit;

import com.example.capitalmarkets.tradesettlement.event.EventType;

import java.util.UUID;
import java.util.List;

public interface AuditService {
    void audit(
            String entityType,
            UUID entityId,
            EventType eventType,
            String username,
            String details
    );

    List<AuditEventResponse> getAuditHistory(
            String entityType,
            UUID entityId
    );
}
