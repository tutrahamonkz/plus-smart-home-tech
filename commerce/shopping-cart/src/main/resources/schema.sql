CREATE TABLE IF NOT EXISTS shopping_carts (
    id UUID DEFAULT gen_random_uuid() PRIMARY KEY,
    user_name VARCHAR
);

CREATE TABLE IF NOT EXISTS cart_products (
    cart_id UUID REFERENCES shopping_carts(id),
    product_id UUID,
    quantity INTEGER
)