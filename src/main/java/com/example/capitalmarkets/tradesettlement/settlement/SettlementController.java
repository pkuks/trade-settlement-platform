package com.example.capitalmarkets.tradesettlement.settlement;

import org.springframework.web.bind.annotation.RestController;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestBody;
import lombok.RequiredArgsConstructor;
import java.util.UUID;

@RestController
@RequestMapping("/api")
@RequiredArgsConstructor
public class SettlementController {

    private final SettlementService settlementService;

    @PostMapping("/trades/{tradeId}/settlements")
    public SettlementResponse createSettlement(
            @PathVariable UUID tradeId
    ){
        return settlementService.createSettlement(tradeId);

    }

    @PostMapping("/settlements/{settlementId}/process")
    public SettlementResponse processSettlement(
            @PathVariable UUID settlementId
    ){
        return settlementService.processSettlement(settlementId);
    }

    @PostMapping("/settlements/{settlementId}/settle")
    public SettlementResponse settle(
            @PathVariable UUID settlementId
    ){
        return settlementService.settle(settlementId);
    }


    @PostMapping("/settlements/{settlementId}/fail")
    public SettlementResponse fail(
            @PathVariable UUID settlementId,
            @RequestBody FailSettlementRequest request
    ){
        return settlementService.fail(settlementId, request.reason());
    }

    @PostMapping("/settlements/{settlementId}/retry")
    public SettlementResponse retry(
            @PathVariable UUID settlementId
    ){
        return settlementService.retry(settlementId);
    }
}
