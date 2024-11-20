-- V1__Create_customers_and_orders.sql
CREATE TABLE customers (
                           id SERIAL PRIMARY KEY,
                           name VARCHAR(255) NOT NULL,
                           phone VARCHAR(20) NOT NULL,
                           address VARCHAR(255) NOT NULL,
                           photo_url VARCHAR(255) NOT NULL
);

CREATE TABLE orders (
                        id SERIAL PRIMARY KEY,
                        order_date DATE NOT NULL,
                        amount DECIMAL(10, 2) NOT NULL,
                        payment_method VARCHAR(50) NOT NULL,
                        customer_id INT NOT NULL,
                        CONSTRAINT fk_customer FOREIGN KEY (customer_id) REFERENCES customers(id) ON DELETE CASCADE
);
