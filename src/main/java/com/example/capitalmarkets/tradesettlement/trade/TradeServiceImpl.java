package com.example.capitalmarkets.tradesettlement.trade;

import com.example.capitalmarkets.tradesettlement.common.exception.InvalidTradeSettlementDateException;
import com.example.capitalmarkets.tradesettlement.common.exception.TradeAlreadyExistsException;
import com.example.capitalmarkets.tradesettlement.common.exception.UnSupportedCurrencyException;
import com.example.capitalmarkets.tradesettlement.user.User;
import com.example.capitalmarkets.tradesettlement.user.UserRepository;
import lombok.RequiredArgsConstructor;
import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Service;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.Set;

@Service
@RequiredArgsConstructor
public class TradeServiceImpl implements TradeService {

    private final TradeRepository tradeRepository;
    private final UserRepository userRepository;

    private static final Set<String> SUPPORTED_CURRENCIES =
            Set.of("USD", "SGD", "EUR", "GBP");

    @Override
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
