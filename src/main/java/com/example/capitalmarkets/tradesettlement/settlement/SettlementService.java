package com.example.capitalmarkets.tradesettlement.settlement;

import java.util.UUID;

public interface SettlementService {

    SettlementResponse createSettlement(UUID tradeId);

    SettlementResponse processSettlement(UUID settlementId);
}
