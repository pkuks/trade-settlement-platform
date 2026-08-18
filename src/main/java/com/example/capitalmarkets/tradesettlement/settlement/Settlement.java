package com.example.capitalmarkets.tradesettlement.settlement;

import com.example.capitalmarkets.tradesettlement.trade.Trade;
import jakarta.persistence.*;

import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(name = "settlements",
    uniqueConstraints = {
        @UniqueConstraint(
            name = "uk_settlement_reference",
            columnNames="settlement_reference")
    }
)
public class Settlement {
    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @OneToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "trade_id")
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

    @Column(nullable = false)
    private LocalDateTime createdAt;

    @Column(nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

}
