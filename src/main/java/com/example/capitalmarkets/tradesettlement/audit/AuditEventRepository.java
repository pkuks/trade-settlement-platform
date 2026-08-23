package com.example.capitalmarkets.tradesettlement.audit;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;
import java.util.List;

public interface AuditEventRepository extends JpaRepository<AuditEvent, UUID> {
    List<AuditEvent> findByEntityTypeAndEntityIdOrderByEventTimeAsc(
            String entityType,
            UUID entityId
    );
}
