package com.example.capitalmarkets.tradesettlement.messaging.dlq;

import org.springframework.data.jpa.repository.JpaRepository;

import java.util.UUID;

public interface FailedEventRepository extends JpaRepository<FailedEvent, UUID> {
}
