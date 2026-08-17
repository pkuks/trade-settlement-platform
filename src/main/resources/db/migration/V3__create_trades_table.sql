CREATE TABLE trades(
    id UUID PRIMARY KEY,
    trade_reference VARCHAR(50) NOT NULL,
    trade_type VARCHAR(10) NOT NULL,
    security_id VARCHAR(50) NOT NULL,
    quantity NUMERIC(19,4) NOT NULL,
    price NUMERIC(19,4) NOT NULL,
    currency VARCHAR(3) NOT NULL,
    trade_date DATE NOT NULL,
    settlement_date DATE NOT NULL,
    status VARCHAR(30) NOT NULL,
    created_by UUID NOT NULL,
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT uk_trades_trade_reference
                   UNIQUE (trade_reference),

    CONSTRAINT fk_trades_created_by
                   FOREIGN KEY (created_by)
                   REFERENCES users(id),

    CONSTRAINT chk_trades_quantity_positive
                   CHECK ( quantity > 0 ),

    CONSTRAINT chk_trades_price_positive
        CHECK ( price > 0 ),

    CONSTRAINT chk_trades_settlement_date
                   CHECK ( settlement_date >= trade_date )

);

CREATE INDEX idx_trades_status
    ON trades(status);

CREATE INDEX idx_trades_trade_date
    ON trades(trade_date);

CREATE INDEX idx_trades_created_by
    ON trades(created_by);