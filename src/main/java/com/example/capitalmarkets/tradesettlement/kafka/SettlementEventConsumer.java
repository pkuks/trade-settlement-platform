package com.example.capitalmarkets.tradesettlement.kafka;

import com.example.capitalmarkets.tradesettlement.event.EventType;
import com.example.capitalmarkets.tradesettlement.event.KafkaTopics;
import com.example.capitalmarkets.tradesettlement.event.SettlementEvent;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import com.example.capitalmarkets.tradesettlement.audit.AuditServiceImpl;
@Component
@RequiredArgsConstructor
@Slf4j
public class SettlementEventConsumer {

    private final AuditServiceImpl auditService;

    @KafkaListener(
            topics = KafkaTopics.SETTLEMENT_EVENTS,
            groupId = "settlement-group"
    )


    public void consume(SettlementEvent event){
        String description = switch (event.eventType()){
            case EventType.SETTLEMENT_CREATED ->  "Settlement created";
            case EventType.SETTLEMENT_PROCESSING -> "Settlement processing";
            case EventType.SETTLEMENT_SETTLED ->  "Settlement settled";
            case EventType.SETTLEMENT_FAILED -> "Settlement failed due to " + event.reason();
            case EventType.SETTLEMENT_RETRIED -> "Settlement retry - Retry count " + event.retryCount();
            default -> throw new IllegalStateException("Unexpected value: " + event.eventType());
        };

        auditService.audit(
                "SETTLEMENT",
                event.settlementId(),
                event.eventType(),
                event.username(),
                description
        );
        log.info("Received settlement event - message : {} , id : {}", description, event.settlementId());
    }
}
