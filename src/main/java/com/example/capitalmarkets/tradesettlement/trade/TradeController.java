package com.example.capitalmarkets.tradesettlement.trade;

import com.example.capitalmarkets.tradesettlement.common.exception.ResourceNotFoundException;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.security.core.Authentication;
import org.springframework.web.bind.annotation.*;

import java.util.UUID;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class TradeController {

    private final TradeService tradeService;

    @PostMapping
    public ResponseEntity<TradeResponse> createTrade(
            @Valid @RequestBody CreateTradeRequest request,
            Authentication authentication){

        return ResponseEntity.status(HttpStatus.CREATED)
                .body(tradeService.createTrade(request, authentication));

    }

    @GetMapping("/{id}")
    public TradeResponse getTrade(
            @PathVariable UUID id){
        return tradeService.getTrade(id);
    }

    @GetMapping
    public Page<TradeResponse> getTrades(Pageable pageable){
        return tradeService.getTrades(pageable);
    }

    @PatchMapping("/{id}/validate")
    public TradeResponse validateTrade(
            @PathVariable UUID id){
        return tradeService.validateTrade(id);
    }

    @PatchMapping("/{id}/ready-for-settlement")
    public TradeResponse markReadyForSettlement(
            @PathVariable UUID id
    ){
        return tradeService.markReadyForSettlement(id);
    }
}
