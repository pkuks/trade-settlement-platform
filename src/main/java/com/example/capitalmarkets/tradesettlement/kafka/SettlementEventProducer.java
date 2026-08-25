package com.example.capitalmarkets.tradesettlement.kafka;

import com.example.capitalmarkets.tradesettlement.event.*;
import org.springframework.stereotype.Service;
import org.springframework.kafka.core.KafkaTemplate;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;

@Service
@RequiredArgsConstructor
@Slf4j
public class SettlementEventProducer {

    private final KafkaTemplate<String, Object> kafkaTemplate;

    public void publishSettlementEvent(
            SettlementEvent event){
        kafkaTemplate.send(
                KafkaTopics.SETTLEMENT_EVENTS,
                event.settlementId().toString(),
                event
        );

        log.info("Published settlement event {} {}", event.eventType(), event.settlementId());
    }

}
