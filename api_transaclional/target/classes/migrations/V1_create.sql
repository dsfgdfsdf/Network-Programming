CREATE TABLE users (
                       id SERIAL PRIMARY KEY,
                       username VARCHAR(50) UNIQUE NOT NULL,
                       password VARCHAR(100) NOT NULL
);

CREATE TABLE transactions (
                              id SERIAL PRIMARY KEY,
                              user_id INT NOT NULL,
                              amount DECIMAL(10, 2) NOT NULL,
                              type VARCHAR(20) NOT NULL CHECK (type IN ('DEPOSIT', 'WITHDRAWAL')),
                              date TIMESTAMP NOT NULL,
                              FOREIGN KEY (user_id) REFERENCES users(id) ON DELETE CASCADE
);