package com.example.capitalmarkets.tradesettlement.outbox;

import com.example.capitalmarkets.tradesettlement.event.EventType;
import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.UUID;

public interface OutboxService {
    void saveEvent(
            EventType eventType,
            UUID aggregateId,
            Object payload
    ) throws JsonProcessingException;
}
