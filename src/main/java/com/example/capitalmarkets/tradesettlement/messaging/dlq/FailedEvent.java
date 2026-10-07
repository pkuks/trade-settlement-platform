package com.example.capitalmarkets.tradesettlement.messaging.dlq;

import com.example.capitalmarkets.tradesettlement.messaging.event.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="failed_events")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class FailedEvent {

    @Id
    private UUID eventId;

    @Enumerated(EnumType.STRING)
    @Column(name="event_type", nullable = false, length = 100)
    private EventType eventType;

    @Column(name="aggregate_id", nullable = false)
    private UUID aggregateId;

    @Column(name="payload", nullable = false)
    private String payload;

    @Column(name="error_message")
    private String errorMessage;

    @Column(name="failed_at", nullable = false)
    private LocalDateTime failedAt;

}
