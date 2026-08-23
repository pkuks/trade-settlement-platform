CREATE TABLE audit_events (
    id UUID PRIMARY KEY,
    entity_type VARCHAR(50) NOT NULL,
    entity_id UUID NOT NULL,
    event_type VARCHAR(100) NOT NULL,
    username VARCHAR(100),
    event_time TIMESTAMP NOT NULL,
    details text
);

CREATE INDEX idx_audit_entity
ON audit_events(entity_type, entity_id);

CREATE INDEX idx_audit_event_time
ON audit_events(event_time);