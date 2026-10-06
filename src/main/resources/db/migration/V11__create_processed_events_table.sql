CREATE TABLE processed_events(
    event_id UUID PRIMARY KEY,
    event_type VARCHAR(100) NOT NULL,
    aggregate_id UUID NOT NULL,
    processed_at TIMESTAMP NOT NULL
);