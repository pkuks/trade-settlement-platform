package com.example.capitalmarkets.tradesettlement.messaging.kafka;

import com.example.capitalmarkets.tradesettlement.messaging.event.SettlementEvent;
import com.example.capitalmarkets.tradesettlement.messaging.outbox.OutboxEvent;
import com.example.capitalmarkets.tradesettlement.messaging.outbox.OutboxEventRepository;
import com.example.capitalmarkets.tradesettlement.messaging.outbox.OutboxStatus;
import com.fasterxml.jackson.databind.ObjectMapper;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.List;

@Component
@Slf4j
@RequiredArgsConstructor
public class OutboxPublisher {

    private final OutboxEventRepository repository;

    private final KafkaTemplate<String, SettlementEvent> kafkaTemplate;

    private final ObjectMapper objectMapper;

    @Scheduled(fixedDelay = 5000)
    @Transactional
    public void publishPendingEvents(){
        List<OutboxEvent> events = repository.findTop100ByStatusOrderByCreatedAtAsc(OutboxStatus.PENDING);

        for (OutboxEvent event: events){
            try {
                SettlementEvent payload = objectMapper.readValue(
                        event.getPayload(),
                        SettlementEvent.class);

                kafkaTemplate.send(KafkaTopics.SETTLEMENT_EVENTS,
                        payload.settlementId().toString(),
                        payload);

                event.setStatus(OutboxStatus.PUBLISHED);
                event.setPublishedAt(LocalDateTime.now());
                log.info("published outbox event {}", event.getId());

            } catch (Exception ex){
                log.error("Failed publishing event {}", event.getId(), ex);
                event.setPublishAttempts(event.getPublishAttempts()+1);
                if (event.getPublishAttempts()>=5){
                    event.setStatus(OutboxStatus.FAILED);
                }

            }
        }
    }
}
