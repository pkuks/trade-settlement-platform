package com.example.capitalmarkets.tradesettlement.messaging.dlq;

import com.example.capitalmarkets.tradesettlement.messaging.event.SettlementEvent;
import com.example.capitalmarkets.tradesettlement.messaging.kafka.KafkaTopics;
import lombok.AllArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
@Slf4j
@AllArgsConstructor
public class SettlementDlqConsumer {

    private final FailedEventService service;

    @KafkaListener(
            topics = KafkaTopics.SETTLEMENT_EVENTS_DLT,
            groupId = "settlement-dlt-group"
    )
//    public void consume(
//            ConsumerRecord<String, String> record) {
//        log.error("DLQ message received : {}", record.value());
//    }
    public void consume(SettlementEvent event){
        log.error("DLQ Event Received. EventId={}, Type={}", event.eventId(), event.eventType());
        service.saveFailedEvent(FailedEvent.builder()
                .eventId(event.eventId())
                .eventType(event.eventType())
                .aggregateId(event.settlementId())
                .payload(event.toString())
                .errorMessage(null)
                .failedAt(LocalDateTime.now())
                .build());
    }

}
