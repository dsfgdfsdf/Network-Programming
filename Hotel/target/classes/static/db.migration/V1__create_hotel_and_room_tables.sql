
CREATE TABLE hotel (
                       id SERIAL PRIMARY KEY,
                       name VARCHAR(255) NOT NULL,
                       location VARCHAR(255) NOT NULL
);


CREATE TABLE room (
                      id SERIAL PRIMARY KEY,
                      room_number VARCHAR(50) NOT NULL,
                      type VARCHAR(50) NOT NULL,
                      price_per_night DECIMAL(10, 2) NOT NULL,
                      is_available BOOLEAN DEFAULT TRUE,
                      hotel_id INT NOT NULL,
                      CONSTRAINT fk_hotel FOREIGN KEY (hotel_id) REFERENCES hotel (id)
);


CREATE TABLE reservation (
                             id BIGSERIAL PRIMARY KEY,
                             guest_name VARCHAR(255) NOT NULL,
                             check_in_date DATE NOT NULL,
                             check_out_date DATE NOT NULL,
                             room_id BIGINT NOT NULL,
                             CONSTRAINT fk_room FOREIGN KEY (room_id) REFERENCES room (id) ON DELETE CASCADE
);

CREATE TABLE product (
                         id BIGSERIAL PRIMARY KEY,
                         name VARCHAR(255) NOT NULL,
                         description TEXT,
                         price DOUBLE PRECISION NOT NULL,
                         is_on_sale BOOLEAN NOT NULL,
                         image_path VARCHAR(500)
);



