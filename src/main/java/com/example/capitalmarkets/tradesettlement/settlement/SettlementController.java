package com.example.capitalmarkets.tradesettlement.settlement;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RestController
@RequestMapping("/api/trades")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping("/{tradeId}/settlements")
    public SettlementResponse createSettlement(
            @PathVariable UUID tradeId
    ){
        return settlementService.createSettlement(tradeId);

    }

    @PostMapping("/{settlementId}/process")
    public SettlementResponse processSettlement(
            @PathVariable UUID settlementId
    ){
        return settlementService.processSettlement(settlementId);
    }


}
