package com.example.capitalmarkets.tradesettlement.outbox;

import com.example.capitalmarkets.tradesettlement.event.EventType;
import jakarta.persistence.*;

import java.util.UUID;
import java.time.LocalDateTime;
import lombok.Data;
import lombok.Builder;
import lombok.NoArgsConstructor;
import lombok.AllArgsConstructor;

@Entity
@Table(name = "outbox_events")
@Data @Builder @NoArgsConstructor @AllArgsConstructor
public class OutboxEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Enumerated(EnumType.STRING)
    @Column(length = 100, nullable = false)
    private EventType eventType;

    @Column(length = 50, nullable = false)
    private String aggregateType;

    @Column(nullable = false)
    private UUID aggregateId;

    @Column(columnDefinition = "TEXT", nullable = false)
    private String payload;

    @Enumerated(EnumType.STRING)
    @Column(length = 20, nullable = false)
    private OutboxStatus status;

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column()
    private LocalDateTime publishedAt;

}
