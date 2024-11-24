
INSERT INTO hotel (name, location) VALUES
                                       ('Grand Hotel', 'Kyiv'),
                                       ('Sea View Resort', 'Odessa'),
                                       ('Mountain Lodge', 'Lviv');


INSERT INTO room (room_number, type, price_per_night, is_available, hotel_id) VALUES
                                                                                  ('101', 'Single', 100.0, true, 1),
                                                                                  ('102', 'Double', 150.0, true, 1),
                                                                                  ('103', 'Suite', 200.0, true, 1),
                                                                                  ('104', 'Single', 120.0, false, 1),
                                                                                  ('201', 'Single', 110.0, true, 2),
                                                                                  ('202', 'Double', 170.0, true, 2),
                                                                                  ('203', 'Suite', 300.0, true, 2),
                                                                                  ('204', 'Single', 130.0, false, 2),
                                                                                  ('301', 'Single', 90.0, true, 3),
                                                                                  ('302', 'Double', 160.0, true, 3),
                                                                                  ('303', 'Suite', 250.0, true, 3),
                                                                                  ('304', 'Single', 140.0, false, 3),
                                                                                  ('401', 'Single', 500.0, true, 1),
                                                                                  ('402', 'Double', 550.0, true, 1),
                                                                                  ('403', 'Suite', 800.0, true, 1),
                                                                                  ('404', 'Single', 120.0, false, 1),
                                                                                  ('501', 'Single', 525.0, true, 2),
                                                                                  ('502', 'Double', 125.0, true, 2),
                                                                                  ('503', 'Suite', 325.0, true, 2),
                                                                                  ('504', 'Single', 135.0, false, 2),
                                                                                  ('601', 'Single', 95.0, true, 3),
                                                                                  ('602', 'Double', 165.0, true, 3),
                                                                                  ('603', 'Suite', 255.0, true, 3),
                                                                                  ('604', 'Single', 145.0, false, 3);
INSERT INTO product (name, description, price, is_on_sale, image_path) VALUES
                                                                           ('SPRITE', 'SPRITE SOFT DRINKS 1,25l', 36.00, true, '/images/SPRITE.png'),
                                                                           ('Mojo', 'Mojo Chinotto non-alcoholic carbonated drink', 19.80, false, '/images/Mojo.png'),
                                                                           ('FANTA', 'FANTA Fanta soft drinks - 1.75 l', 46.00, true, '/images/FANTA.png'),
                                                                           ('Mojo', 'Mojo Chinotto carbonated soft drink 0.33 l', 450.00, false, '/images/MOJO_banc.png'),
                                                                           ('Zhivchik', 'Soft drink Zhivchik 2 l', 45.70, true, '/images/Zhivchik.png'),
                                                                           ('Zhivchik ORANGE', 'Soft drink Zhivchik ORANGE 0.33 l', 15.20, true, '/images/Zhivchik_ORANGE.png'),
                                                                           ('Luck Siam', 'Luck Siam non-alcoholic drink with Basil seeds Cocktail 290 ml', 77.80, true, '/images/Luck_Siam.png'),
                                                                           ('Battery', 'Battery non-alcoholic, highly carbonated energy drink 0.5l', 26.99, false, '/images/Batery.png'),
                                                                           ('Coca-Cola Zero', 'Drink Coca-Cola Zero Non-alcoholic highly carbonated Without sugar 1.25 l', 61.00, true, '/images/Coca_cola_zero.png'),
                                                                           ('Coca Cola Marvel Captain America', 'Drink Coca Cola Marvel Captain America Zero Sugar 330 ml', 75.00, false, '/images/Captain_America.png');
