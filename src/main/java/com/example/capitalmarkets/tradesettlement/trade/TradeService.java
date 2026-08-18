package com.example.capitalmarkets.tradesettlement.trade;

import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;

import java.util.UUID;

public interface TradeService {

    TradeResponse createTrade(CreateTradeRequest request, Authentication authentication);

    TradeResponse getTrade(UUID tradeId);

    Page<TradeResponse> getTrades(Pageable pageable);
}
