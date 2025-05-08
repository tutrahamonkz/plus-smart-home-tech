CREATE TABLE IF NOT EXISTS orders (
    order_id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    shopping_cart_id UUID,
    payment_id UUID,
    delivery_id UUID,
    state VARCHAR,
    delivery_weight NUMERIC,
    delivery_volume NUMERIC,
    fragile BOOLEAN,
    total_price INT,
    delivery_price INT,
    product_price INT,
    user_name VARCHAR
);

CREATE TABLE IF NOT EXISTS order_products (
    order_id UUID REFERENCES orders(order_id),
    product_id UUID,
    quantity INTEGER
)