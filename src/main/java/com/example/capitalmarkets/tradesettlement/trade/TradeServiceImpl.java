package com.example.capitalmarkets.tradesettlement.trade;

import com.example.capitalmarkets.tradesettlement.common.exception.*;
import com.example.capitalmarkets.tradesettlement.user.User;
import com.example.capitalmarkets.tradesettlement.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDateTime;
import java.util.Set;
import java.util.UUID;

@Service
@RequiredArgsConstructor
@Transactional(readOnly = true)
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;

    private static final Set<String> SUPPORTED_CURRENCIES =
            Set.of("USD", "SGD", "EUR", "GBP");

    @Override
    @Transactional
    public TradeResponse createTrade(CreateTradeRequest request, Authentication authentication) {
        validateRequest(request);

        if (tradeRepository.existsByTradeReference(request.tradeReference())){
            throw new TradeAlreadyExistsException("Trade reference already exists");
        }

        User user = userRepository.findByUsername(authentication.getName())
                .orElseThrow();

        Trade trade = new Trade();
        trade.setTradeReference(request.tradeReference());
        trade.setTradeType(TradeType.valueOf(request.tradeType()));
        trade.setSecurityId(request.securityId());
        trade.setQuantity(request.quantity());
        trade.setPrice(request.price());
        trade.setCurrency(request.currency());
        trade.setTradeDate(request.tradeDate());
        trade.setSettlementDate(request.settlementDate());
        trade.setStatus(TradeStatus.NEW);
        trade.setCreatedBy(user);
        trade.setCreatedAt(LocalDateTime.now());
        trade.setUpdatedAt(LocalDateTime.now());

        Trade savedTrade = tradeRepository.save(trade);
        return map(trade);
    }

    @Override
    public TradeResponse getTrade(UUID tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(()-> new ResourceNotFoundException("Trade not found"));
        return map(trade);
    }

    @Override
    public Page<TradeResponse> getTrades(Pageable pageable) {
        return tradeRepository.findAll(pageable)
                .map(this::map);
    }

    @Override
    @Transactional
    public TradeResponse validateTrade(UUID tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(() -> new ResourceNotFoundException("Trade not found"));

        trade.markValidated();
        trade.setUpdatedAt(LocalDateTime.now());
        return map(trade);
    }

    @Override
    @Transactional
    public TradeResponse markReadyForSettlement(UUID tradeId) {
        Trade trade = tradeRepository.findById(tradeId)
                .orElseThrow(()-> new ResourceNotFoundException("Trade not found"));

        trade.markReadyForSettlement();
        trade.setUpdatedAt(LocalDateTime.now());
        return map(trade);
    }

    private void validateRequest(CreateTradeRequest request){
        if (request.settlementDate().isBefore(request.tradeDate())){
            throw new InvalidTradeSettlementDateException("Settlement date cannot be before trade date");
        }
        if (!SUPPORTED_CURRENCIES.contains(request.currency().toUpperCase())){
            throw new UnSupportedCurrencyException("Unsupported currency");
        }
    }

    private TradeResponse map(Trade trade){
        return new TradeResponse(
                trade.getId(),
                trade.getTradeReference(),
                trade.getTradeType(),
                trade.getSecurityId(),
                trade.getQuantity(),
                trade.getPrice(),
                trade.getCurrency(),
                trade.getTradeDate(),
                trade.getSettlementDate(),
                trade.getStatus(),
                trade.getCreatedBy().getUsername()
        );
    }


}
