CREATE TABLE IF NOT EXISTS addresses (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    country VARCHAR,
    city VARCHAR,
    street VARCHAR,
    house VARCHAR,
    flat VARCHAR
);

CREATE TABLE IF NOT EXISTS warehouses (
    id BIGINT GENERATED BY DEFAULT AS IDENTITY PRIMARY KEY,
    address BIGINT REFERENCES addresses(id)
);

CREATE TABLE IF NOT EXISTS warehouse_products (
    id UUID PRIMARY KEY,
    fragile BOOLEAN,
    weight DOUBLE PRECISION,
    width DOUBLE PRECISION,
    height DOUBLE PRECISION,
    depth DOUBLE PRECISION,
    quantity INT
);

CREATE TABLE IF NOT EXISTS order_bookings (
    order_id UUID PRIMARY KEY,
    delivery_id UUID
);

CREATE TABLE IF NOT EXISTS warehouses_warehouse_products (
    warehouse_id BIGINT REFERENCES warehouses(id),
    warehouse_product_id UUID REFERENCES warehouse_products(id),
    CONSTRAINT pk_warehouses_warehouse_products PRIMARY KEY (warehouse_id, warehouse_product_id)
);

CREATE TABLE IF NOT EXISTS bookings_warehouse_products (
    order_id UUID REFERENCES order_bookings(order_id),
    warehouse_product_id UUID REFERENCES warehouse_products(id),
    CONSTRAINT pk_bookings_warehouse_products PRIMARY KEY (order_id, warehouse_product_id)
);