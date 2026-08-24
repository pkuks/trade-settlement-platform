package com.example.capitalmarkets.tradesettlement.kafka;

import com.example.capitalmarkets.tradesettlement.event.KafkaTopics;
import com.example.capitalmarkets.tradesettlement.event.SettlementCreatedEvent;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettlementEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSettlementCreated(
            SettlementCreatedEvent event){
        var future = kafkaTemplate.send(
                KafkaTopics.SETTLEMENT_CREATED,
                event.settlementId().toString(),
                event
        );

        // Capture the exact partition and topic details straight from the broker
        future.whenComplete((result, ex) -> {
            if (ex == null) {
                log.info("BROKER CONFIRMATION -> Topic: [{}], Partition: [{}], Offset: [{}]",
                        result.getRecordMetadata().topic(),
                        result.getRecordMetadata().partition(),
                        result.getRecordMetadata().offset());
            } else {
                log.error("BROKER ERROR -> Failed to write message", ex);
            }
        });


        log.info("Published settlement created event {}", event.settlementId());
    }
}
