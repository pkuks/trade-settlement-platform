package com.example.capitalmarkets.tradesettlement.trade;

import com.example.capitalmarkets.tradesettlement.common.exception.BusinessException;
import com.example.capitalmarkets.tradesettlement.user.User;
import jakarta.persistence.*;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.math.BigDecimal;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.UUID;

@Entity
@Table(
        name="trades",
        uniqueConstraints = {
                @UniqueConstraint(
                    name = "uk_trades_trade_reference",
                    columnNames="trade_reference"
                )
        }
)
@Getter @Setter @AllArgsConstructor @NoArgsConstructor
public class Trade {

    @Id
    @GeneratedValue(strategy = GenerationType.UUID)
    private UUID id;

    @Column(name = "trade_reference", nullable = false, length = 50)
    private String tradeReference;

    @Enumerated(EnumType.STRING)
    @Column(name = "trade_type", nullable = false, length = 10)
    private TradeType tradeType;

    @Column(name="security_id", nullable = false, length = 50)
    private String securityId;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal quantity;

    @Column(nullable = false, precision = 19, scale = 4)
    private BigDecimal price;

    @Column(nullable = false, length = 3)
    private String currency;

    @Column(name="trade_date", nullable = false)
    private LocalDate tradeDate;

    @Column(name="settlement_date", nullable = false)
    private LocalDate settlementDate;

    @Enumerated(EnumType.STRING)
    @Column(nullable = false, length = 30)
    private TradeStatus status;

    @ManyToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name="created_by", nullable = false)
    private User createdBy;

    @Column(name="created_at", nullable = false)
    private LocalDateTime createdAt;

    @Column(name="updated_at", nullable = false)
    private LocalDateTime updatedAt;

    @Version
    @Column(nullable = false)
    private Long version;

    public void markValidated(){
        if (status != TradeStatus.NEW){
            throw new BusinessException("Only trades with NEW status can be validated");
        }
        status = TradeStatus.VALIDATED;
    }

    public void markReadyForSettlement(){
        boolean isValidState = (status == TradeStatus.VALIDATED || status == TradeStatus.FAILED);

        if (!isValidState) {
            throw new BusinessException("Only trades in validated or failed status can be ready for settlement");
        }

        status = TradeStatus.READY_FOR_SETTLEMENT;
    }

}
