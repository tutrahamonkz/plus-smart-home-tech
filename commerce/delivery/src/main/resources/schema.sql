CREATE TABLE IF NOT EXISTS orders (
    delivery_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    from_address UUID,
    to_address UUID,
    order_id UUID,
    delivery_state VARCHAR
);