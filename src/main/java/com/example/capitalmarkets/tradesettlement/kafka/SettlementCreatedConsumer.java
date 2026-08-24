package com.example.capitalmarkets.tradesettlement.kafka;

import com.example.capitalmarkets.tradesettlement.audit.AuditEventType;
import com.example.capitalmarkets.tradesettlement.event.KafkaTopics;
import com.example.capitalmarkets.tradesettlement.event.SettlementCreatedEvent;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import com.example.capitalmarkets.tradesettlement.audit.AuditServiceImpl;
@Component
@RequiredArgsConstructor
@Slf4j
public class SettlementCreatedConsumer {

    private final AuditServiceImpl auditService;

    @KafkaListener(
            topics = KafkaTopics.SETTLEMENT_CREATED,
            groupId = "audit-group"
    )
    public void consume(SettlementCreatedEvent event){
        auditService.audit(
                "SETTLEMENT",
                event.settlementId(),
                AuditEventType.SETTLEMENT_CREATED,
                event.username(),
                "Settlement created"
        );

        log.info("Received settlement created event {}", event.settlementId());

    }
}
