ALTER TABLE outbox_events
    ALTER COLUMN publish_attempts SET DEFAULT 0,
    ALTER COLUMN publish_attempts TYPE integer USING COALESCE(publish_attempts, 0),
    ALTER COLUMN publish_attempts SET NOT NULL;
