package com.example.capitalmarkets.tradesettlement.messaging.processed;

import com.example.capitalmarkets.tradesettlement.messaging.event.EventType;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name="processed_events")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class ProcessedEvent {

    @Id
    private UUID eventId;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 100)
    private EventType eventType;

    @Column(nullable = false)
    private UUID aggregateId;

    @Column(nullable = false)
    private LocalDateTime processedAt;

}
