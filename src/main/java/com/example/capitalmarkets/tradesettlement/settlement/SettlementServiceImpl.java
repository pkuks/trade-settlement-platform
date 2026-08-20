package com.example.capitalmarkets.tradesettlement.settlement;

import java.util.UUID;

import com.example.capitalmarkets.tradesettlement.common.exception.ResourceNotFoundException;
import com.example.capitalmarkets.tradesettlement.trade.TradeRepository;
import com.example.capitalmarkets.tradesettlement.trade.Trade;
import com.example.capitalmarkets.tradesettlement.trade.TradeStatus;
import com.example.capitalmarkets.tradesettlement.common.exception.BusinessException;
import com.example.capitalmarkets.tradesettlement.common.util.ReferenceGenerator;
import java.time.LocalDateTime;

import org.springframework.stereotype.Service;

@Service
public class SettlementServiceImpl implements SettlementService {

    private final TradeRepository tradeRepository;

    private final SettlementRepository settlementRepository;

    public SettlementServiceImpl(
            SettlementRepository settlementRepository,
            TradeRepository tradeRepository
    ){
        this.settlementRepository = settlementRepository;
        this.tradeRepository = tradeRepository;
    }

    @Override
    public SettlementResponse createSettlement(UUID tradeId) {

        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(()-> new ResourceNotFoundException("Trade not found"));

        if (trade.getStatus() != TradeStatus.READY_FOR_SETTLEMENT){
            throw new BusinessException("Trade must be ready for settlement");
        }

        if (settlementRepository.existsByTradeId(tradeId)){
            throw new BusinessException("Settlement already exists for this trade");
        }

        Settlement settlement = new Settlement();
        settlement.setTrade(trade);
        settlement.setSettlementReference(ReferenceGenerator.settlementReference());
        settlement.setStatus(SettlementStatus.PENDING);
        settlement.setCreatedAt(LocalDateTime.now());
        settlement.setUpdatedAt(LocalDateTime.now());

        Settlement save = settlementRepository.save(settlement);

        return map(settlement);
    }

    public SettlementResponse map(Settlement settlement){
        return new SettlementResponse(
                settlement.getId(),
                settlement.getSettlementReference(),
                settlement.getTrade().getTradeReference(),
                settlement.getStatus()
        );
    }
}
