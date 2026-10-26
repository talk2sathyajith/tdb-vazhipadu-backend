
-- Supports the default stable order: newest order first,
-- with id as a unique tie-breaker.
CREATE INDEX idx_temple_orders_created_at_id
    ON temple_orders (created_at DESC, id DESC);

-- Supports filtering by status and then ordering by creation time.
CREATE INDEX idx_temple_orders_status_created_at_id
    ON temple_orders (status, created_at DESC, id DESC);

-- Supports searching by the beginning of a phone number.
CREATE INDEX idx_temple_orders_customer_phone
    ON temple_orders (customer_phone text_pattern_ops);
