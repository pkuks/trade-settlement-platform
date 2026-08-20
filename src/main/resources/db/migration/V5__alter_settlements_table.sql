ALTER TABLE settlements
    ADD CONSTRAINT uk_settlement_trade UNIQUE (trade_id);
