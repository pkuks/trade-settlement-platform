package com.example.capitalmarkets.tradesettlement.messaging.dlq;

import lombok.AllArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@AllArgsConstructor
public class FailedEventService {

    private final FailedEventRepository repository;

    public void saveFailedEvent(FailedEvent event){
        repository.save(event);
    }
}
