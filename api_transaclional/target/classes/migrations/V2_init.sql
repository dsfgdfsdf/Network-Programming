-- Adding users
INSERT INTO users (username, password) VALUES
                                           ('user1', 'password1'),
                                           ('user2', 'password2');

-- Adding transactions
INSERT INTO transactions (user_id, amount, type, date) VALUES
                                                           (1, 1000.00, 'DEPOSIT', '2024-01-10 10:00:00'),
                                                           (1, 500.00, 'WITHDRAWAL', '2024-02-15 12:30:00'),
                                                           (1, 1200.00, 'DEPOSIT', '2024-03-20 14:45:00'),
                                                           (2, 200.00, 'WITHDRAWAL', '2024-01-05 09:15:00'),
                                                           (2, 1500.00, 'DEPOSIT', '2024-02-25 16:20:00');