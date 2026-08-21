package com.example.capitalmarkets.tradesettlement.settlement;

import java.util.UUID;

public interface SettlementService {

    SettlementResponse createSettlement(UUID tradeId);

    SettlementResponse processSettlement(UUID settlementId);

    SettlementResponse settle(UUID settlementId);

    SettlementResponse fail(UUID settlementId, String reason);

    SettlementResponse retry(UUID settlementId);
}
