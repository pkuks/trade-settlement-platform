package com.example.capitalmarkets.tradesettlement.trade;

import org.springframework.security.core.Authentication;

public interface TradeService {

    TradeResponse createTrade(CreateTradeRequest request, Authentication authentication);
}
