CREATE TABLE settlements (
    id UUId PRIMARY KEY,
    trade_id UUID NOT NULL,
    status VARCHAR(30) NOT NULL,
    settlement_reference VARCHAR(50) NOT NULL,
    settled_at TIMESTAMP,
    failure_reason VARCHAR(500),
    created_at TIMESTAMP NOT NULL,
    updated_at TIMESTAMP NOT NULL,
    version BIGINT NOT NULL DEFAULT 0,

    CONSTRAINT uk_settlement_reference
        UNIQUE (settlement_reference),

    CONSTRAINT fk_settlement_trade
        FOREIGN KEY (trade_id)
        REFERENCES trades(id)
);