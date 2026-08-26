package com.example.capitalmarkets.tradesettlement.outbox;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.springframework.stereotype.Service;
import com.example.capitalmarkets.tradesettlement.event.EventType;
import java.util.UUID;
import lombok.RequiredArgsConstructor;
import java.time.LocalDateTime;
import org.springframework.transaction.annotation.Transactional;

@Service
@RequiredArgsConstructor
public class OutboxServiceImpl implements OutboxService {

    private final ObjectMapper objectMapper;
    private final OutboxEventRepository repository;

    @Override
    @Transactional
    public void saveEvent(EventType eventType,
                   UUID aggregateId,
                   Object payload
    ) throws JsonProcessingException {
        OutboxEvent event = OutboxEvent.builder()
                .eventType(eventType)
                .aggregateId(aggregateId)
                .aggregateType("SETTLEMENT")
                .payload(objectMapper.writeValueAsString(payload))
                .status(OutboxStatus.PENDING)
                .createdAt(LocalDateTime.now())
                .build();
        repository.save(event);
    }
}
