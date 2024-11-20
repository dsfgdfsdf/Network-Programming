INSERT INTO customers (name, phone, address, photo_url)
VALUES
    ('Іван Іваненко', '+380123456789', 'Київ, вул. Хрещатик, 1', '/images/ivan.jpg'),
    ('Петро Петренко', '+380987654321', 'Одеса, вул. Дерибасівська, 10', '/images/petro.jpg');

INSERT INTO orders (order_date, amount, payment_method, customer_id)
VALUES
    ('2024-10-15', 1500.50, 'Credit Card', 1),
    ('2024-10-16', 2000.75, 'Cash', 1),
    ('2024-10-17', 1200.00, 'Bank Transfer', 2);