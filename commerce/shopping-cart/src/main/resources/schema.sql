CREATE TABLE IF NOT EXISTS shopping_carts (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    user_name VARCHAR
);

CREATE TABLE IF NOT EXISTS cart_products (
    cart_id BIGINT REFERENCES shopping_carts(id),
    product_id UUID,
    quantity INTEGER
)