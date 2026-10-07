package com.example.capitalmarkets.tradesettlement.messaging.kafka;

import com.example.capitalmarkets.tradesettlement.audit.AuditService;
import com.example.capitalmarkets.tradesettlement.audit.AuditServiceImpl;
import com.example.capitalmarkets.tradesettlement.messaging.event.EventType;
import com.example.capitalmarkets.tradesettlement.messaging.event.SettlementEvent;
import com.example.capitalmarkets.tradesettlement.messaging.processed.ProcessedEvent;
import com.example.capitalmarkets.tradesettlement.messaging.processed.ProcessedEventRepository;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.InjectMocks;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;

import java.time.Instant;
import java.util.UUID;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
public class SettlementEventConsumerTest {

    @InjectMocks
    private SettlementEventConsumer consumer;

    @Mock
    private AuditServiceImpl auditService;

    @Mock
    private ProcessedEventRepository repository;

    @Test
    void testConsumeTwiceIsIdempotentWithMocks() {
        SettlementEvent event = SettlementEvent.builder().
                eventId(UUID.fromString("c7493d19-c58c-4337-bcd2-4baccb768727")).
                eventType(EventType.SETTLEMENT_CREATED).
                settlementId(UUID.randomUUID()).
                tradeId(UUID.randomUUID()).
                //settlementId(UUID.fromString("c21e8f79-4eae-4e37-b16e-10f62bd21991")).
                //tradeId(UUID.fromString("4a8f4272-188c-4d00-9b11-d2b22e2d3577")).
                settlementReference("SET-2610F84F1").
                username("Danny").
                reason(null).
                retryCount(null).
                eventTime(Instant.now()).build();

        // Configure the mock repository:
        // 1st invocation of existsById() returns false
        // 2nd invocation of existsById() returns true
        when(repository.existsById(event.eventId()))
                .thenReturn(false)
                .thenReturn(true);

        // Act: Invoke the consumer the FIRST time (should process fully)
        consumer.consume(event);

        // Act: Invoke the consumer the SECOND time (should hit the "true" stub and return early)
        consumer.consume(event);

        // Assert: Verify that the side-effects occurred exactly ONCE across the entire lifecycle

        // 1. Core business logic (audit service) should only have been triggered once
        verify(auditService, times(1)).audit(
                eq("SETTLEMENT"),
                eq(event.settlementId()),
                eq(event.eventType()),
                eq(event.username()),
                anyString()
        );

        // 2. The repository save operation should only have been attempted once
        verify(repository, times(1)).save(any(ProcessedEvent.class));

    }
}
