CREATE TABLE failed_events(
    event_id UUID PRIMARY KEY,
    event_type VARCHAR(100) NOT NULL ,
    aggregate_id UUID NOT NULL ,
    payload TEXT NOT NULL ,
    error_message TEXT,
    failed_at TIMESTAMP NOT NULL
);

CREATE INDEX idx_failed_events_aggregate_id
    ON failed_events(aggregate_id);