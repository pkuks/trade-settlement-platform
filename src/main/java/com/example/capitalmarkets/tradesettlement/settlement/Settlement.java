package com.example.capitalmarkets.tradesettlement.settlement;

import com.example.capitalmarkets.tradesettlement.trade.Trade;
import com.example.capitalmarkets.tradesettlement.common.exception.BusinessException;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "settlements",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_settlement_reference",
            columnNames="settlement_reference"),
        @UniqueConstraint(
                name = "uk_settlement_trade",
                columnNames="trade_id")
    }
)
@Getter
@Setter
@NoArgsConstructor
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "trade_id", nullable = false)
    private Trade trade;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private SettlementStatus status;

    @Column(name = "settlement_reference", nullable = false, length = 50)
    private String settlementReference;

    @Column(name = "settled_at")
    private LocalDateTime settledAt;

    @Column(name = "failure_reason", length = 500)
    private String failureReason;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    public void markProcessing(){
        if (status != SettlementStatus.PENDING){
            throw new BusinessException("Settlement must be PENDING");
        }
        status = SettlementStatus.PROCESSING;
    }

    public void markSettled(){
        if (status != SettlementStatus.PROCESSING){
            throw new BusinessException("Settlement must be PROCESSING");
        }
        status = SettlementStatus.SETTLED;
    }

}
