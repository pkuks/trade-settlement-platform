package com.example.capitalmarkets.tradesettlement.audit;

import java.util.UUID;
import java.lang.Override;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;
import org.springframework.transaction.annotation.Transactional;
import java.time.LocalDateTime;
import java.util.List;

@Service
@AllArgsConstructor
public class AuditServiceImpl implements AuditService {

    private final AuditEventRepository auditEventRepository;

    @Override
    @Transactional
    public void audit(
            String entityType,
            UUID entityId,
            AuditEventType eventType,
            String username,
            String details
    ){
        AuditEvent auditEvent = AuditEvent.builder()
                .entityType(entityType)
                .entityId(entityId)
                .eventType(eventType.name())
                .username(username)
                .details(details)
                .eventTime(LocalDateTime.now())
                .build();

        auditEventRepository.save(auditEvent);
    }

    @Override
    public List<AuditEventResponse> getAuditHistory(
            String entityType, UUID entityId
    ){

        return auditEventRepository.findByEntityTypeAndEntityIdOrderByEventTimeAsc(
                entityType, entityId)
                .stream()
                .map(this::map)
                .toList();
    }

    public AuditEventResponse map(AuditEvent auditEvent){
        return new AuditEventResponse(
                auditEvent.getEventType(), auditEvent.getUsername(), auditEvent.getEventTime(), auditEvent.getDetails()
        );
    }

}