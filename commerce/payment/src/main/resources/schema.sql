CREATE TABLE IF NOT EXISTS payments (
    payment_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    order_id UUID,
    total_payment NUMERIC,
    delivery_total NUMERIC,
    fee_total NUMERIC,
    state VARCHAR
);