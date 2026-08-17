package com.example.capitalmarkets.tradesettlement.trade;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;
import java.util.UUID;

@Repository
public interface TradeRepository extends JpaRepository<Trade, UUID> {
    Optional<Trade> findByTradeReference(String tradeReference);

    boolean existsByTradeReference(String tradeReference);
}
