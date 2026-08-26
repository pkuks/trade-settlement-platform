package com.example.capitalmarkets.tradesettlement.settlement;

import com.fasterxml.jackson.core.JsonProcessingException;

import java.util.UUID;

public interface SettlementService {

    SettlementResponse createSettlement(UUID tradeId) throws JsonProcessingException;

    SettlementResponse processSettlement(UUID settlementId) throws JsonProcessingException;

    SettlementResponse settle(UUID settlementId) throws JsonProcessingException;

    SettlementResponse fail(UUID settlementId, String reason) throws JsonProcessingException;

    SettlementResponse retry(UUID settlementId) throws JsonProcessingException;
}
