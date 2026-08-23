package com.example.capitalmarkets.tradesettlement.audit;

import jakarta.persistence.Entity;
import jakarta.persistence.Column;
import jakarta.persistence.Table;
import jakarta.persistence.Id;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import java.util.UUID;
import java.time.LocalDateTime;
import lombok.Getter;
import lombok.Setter;
import lombok.NoArgsConstructor;
import lombok.Builder;
import lombok.AllArgsConstructor;

@Entity
@Table(name="audit_events")
@Getter @Setter @NoArgsConstructor @AllArgsConstructor
@Builder
public class AuditEvent {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(length = 50, nullable = false)
    private String entityType;

    @Column(nullable = false)
    private UUID entityId;

    @Column(length = 100, nullable = false)
    private String eventType;

    @Column(length = 100, nullable = false)
    private String username;

    @Column(nullable = false)
    private LocalDateTime eventTime;

    @Column(columnDefinition = "TEXT")
    private String details;
}
