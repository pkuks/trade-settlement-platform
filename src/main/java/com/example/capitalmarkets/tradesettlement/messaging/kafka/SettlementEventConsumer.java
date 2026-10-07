package com.example.capitalmarkets.tradesettlement.messaging.kafka;

import com.example.capitalmarkets.tradesettlement.messaging.event.EventType;
import com.example.capitalmarkets.tradesettlement.messaging.event.SettlementEvent;
import com.example.capitalmarkets.tradesettlement.messaging.processed.ProcessedEvent;
import com.example.capitalmarkets.tradesettlement.messaging.processed.ProcessedEventRepository;
import org.springframework.dao.DataIntegrityViolationException;
import org.springframework.stereotype.Component;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import com.example.capitalmarkets.tradesettlement.audit.AuditServiceImpl;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;

@Component
@RequiredArgsConstructor
@Slf4j
public class SettlementEventConsumer {

    private final AuditServiceImpl auditService;

    private final ProcessedEventRepository repository;

    @KafkaListener(
            topics = KafkaTopics.SETTLEMENT_EVENTS,
            groupId = "settlement-group"
    )


    @Transactional
    public void consume(SettlementEvent event){

        if (repository.existsById(event.eventId())){
            log.info("Duplicate event ignored {}", event.eventId());
            return;
        }

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
        try {
            repository.save(new ProcessedEvent(
                    event.eventId(), event.eventType(), event.settlementId(), LocalDateTime.now()
            ));
        } catch(DataIntegrityViolationException ex){
            log.info("Duplicate event ignored {}", event.eventId());
        }
    }
}
