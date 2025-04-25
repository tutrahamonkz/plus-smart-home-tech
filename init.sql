CREATE DATABASE analyzer;
CREATE DATABASE store;
CREATE DATABASE cart;
CREATE DATABASE warehouse;
CREATE DATABASE order_base;
CREATE DATABASE delivery;

CREATE USER analyzer_user WITH PASSWORD 'password1';
CREATE USER store_user WITH PASSWORD 'password2';
CREATE USER cart_user WITH PASSWORD 'password3';
CREATE USER warehouse_user WITH PASSWORD 'password4';
CREATE USER order_user WITH PASSWORD 'password5';
CREATE USER delivery_user WITH PASSWORD 'password6';

ALTER DATABASE analyzer OWNER TO analyzer_user;
ALTER DATABASE store OWNER TO store_user;
ALTER DATABASE cart OWNER TO cart_user;
ALTER DATABASE warehouse OWNER TO warehouse_user;
ALTER DATABASE order_base OWNER TO order_user;
ALTER DATABASE delivery OWNER TO delivery_user;