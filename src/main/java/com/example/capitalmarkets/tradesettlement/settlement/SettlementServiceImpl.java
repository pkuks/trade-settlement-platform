package com.example.capitalmarkets.tradesettlement.settlement;

import java.util.UUID;

import com.example.capitalmarkets.tradesettlement.event.EventType;
import com.example.capitalmarkets.tradesettlement.common.exception.ResourceNotFoundException;
import com.example.capitalmarkets.tradesettlement.event.SettlementEvent;
import com.example.capitalmarkets.tradesettlement.kafka.SettlementEventProducer;
import com.example.capitalmarkets.tradesettlement.trade.TradeRepository;
import com.example.capitalmarkets.tradesettlement.trade.Trade;
import com.example.capitalmarkets.tradesettlement.trade.TradeStatus;
import com.example.capitalmarkets.tradesettlement.common.exception.BusinessException;
import com.example.capitalmarkets.tradesettlement.common.util.ReferenceGenerator;
import java.time.LocalDateTime;
import com.example.capitalmarkets.tradesettlement.audit.AuditService;

import org.springframework.transaction.annotation.Transactional;
import org.springframework.stereotype.Service;
import lombok.AllArgsConstructor;

@Service
@AllArgsConstructor
public class SettlementServiceImpl implements SettlementService {

    private final TradeRepository tradeRepository;

    private final SettlementRepository settlementRepository;

    private final AuditService auditEventService;

    private final SettlementEventProducer settlementEventProducer;

    @Override
    @Transactional
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

        Settlement saved = settlementRepository.save(settlement);
        settlementEventProducer.publishSettlementEvent(new SettlementEvent(
                EventType.SETTLEMENT_CREATED,
                saved.getId(),
                trade.getId(),
                saved.getSettlementReference(),
                trade.getCreatedBy().getUsername(),
                null,
                null
        ));

        return map(settlement);
    }

    @Override
    @Transactional
    public SettlementResponse processSettlement(UUID settlementId){
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(()-> new ResourceNotFoundException("Settlement not found"));

        settlement.markProcessing();
        settlement.setUpdatedAt(LocalDateTime.now());

        Trade trade = settlement.getTrade();
        trade.setStatus(TradeStatus.SETTLING);
        trade.setUpdatedAt(LocalDateTime.now());
        settlementEventProducer.publishSettlementEvent(new SettlementEvent(
                EventType.SETTLEMENT_PROCESSING,
                settlement.getId(),
                trade.getId(),
                settlement.getSettlementReference(),
                trade.getCreatedBy().getUsername(),
                null,
                null
        ));

        return map(settlement);
    }

    @Override
    @Transactional
    public SettlementResponse settle(UUID settlementId){
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(()-> new ResourceNotFoundException("Settlement not found"));

        settlement.markSettled();
        settlement.setSettledAt(LocalDateTime.now());
        settlement.setUpdatedAt(LocalDateTime.now());

        Trade trade = settlement.getTrade();
        trade.setStatus(TradeStatus.SETTLED);
        trade.setUpdatedAt(LocalDateTime.now());

        settlementEventProducer.publishSettlementEvent(new SettlementEvent(
                EventType.SETTLEMENT_SETTLED,
                settlement.getId(),
                trade.getId(),
                settlement.getSettlementReference(),
                trade.getCreatedBy().getUsername(),
                null,
                null
        ));
        return map(settlement);

    }

    @Override
    @Transactional
    public SettlementResponse fail(UUID settlementId, String reason){
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(()-> new ResourceNotFoundException("Settlement Not found"));
        settlement.markFailed();
        settlement.setFailureReason(reason);
        settlement.setSettledAt(LocalDateTime.now());
        settlement.setUpdatedAt(LocalDateTime.now());

        Trade trade = settlement.getTrade();
        trade.setStatus(TradeStatus.FAILED);
        trade.setUpdatedAt(LocalDateTime.now());
        settlementEventProducer.publishSettlementEvent(new SettlementEvent(
                EventType.SETTLEMENT_FAILED,
                settlement.getId(),
                trade.getId(),
                settlement.getSettlementReference(),
                trade.getCreatedBy().getUsername(),
                reason,
                null
        ));
        return map(settlement);

    }

    @Override
    @Transactional
    public SettlementResponse retry(UUID settlementId){
        Settlement settlement = settlementRepository.findById(settlementId)
                .orElseThrow(()-> new ResourceNotFoundException("Settlement not found"));
        settlement.retry();
        settlement.setUpdatedAt(LocalDateTime.now());
        Trade trade = settlement.getTrade();
        trade.markReadyForSettlement();
        trade.setUpdatedAt(LocalDateTime.now());
        settlementEventProducer.publishSettlementEvent(new SettlementEvent(
                EventType.SETTLEMENT_RETRIED,
                settlement.getId(),
                trade.getId(),
                settlement.getSettlementReference(),
                trade.getCreatedBy().getUsername(),
                null,
                settlement.getRetryCount()
        ));
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
