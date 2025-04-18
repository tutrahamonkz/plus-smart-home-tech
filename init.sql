CREATE DATABASE analyzer;
CREATE DATABASE store;
CREATE DATABASE cart;
CREATE DATABASE warehouse;

CREATE USER analyzer_user WITH PASSWORD 'password1';
CREATE USER store_user WITH PASSWORD 'password2';
CREATE USER cart_user WITH PASSWORD 'password3';
CREATE USER warehouse_user WITH PASSWORD 'password4';

ALTER DATABASE analyzer OWNER TO analyzer_user;
ALTER DATABASE store OWNER TO store_user;
ALTER DATABASE cart OWNER TO cart_user;
ALTER DATABASE warehouse OWNER TO warehouse_user;