-- 🔄 Réinitialisation
DROP DATABASE IF EXISTS decoder_simulator;
CREATE DATABASE decoder_simulator;
USE decoder_simulator;

-- 1. CLIENT
CREATE TABLE client (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

-- 2. USER
-- Note: USER est un mot réservé en MySQL, on le protège ici avec des backticks
CREATE TABLE `user` (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        username VARCHAR(100) NOT NULL UNIQUE,
                        password VARCHAR(255) NOT NULL,
                        role VARCHAR(20) NOT NULL,
                        client_id BIGINT,
                        CONSTRAINT fk_user_client FOREIGN KEY (client_id)
                            REFERENCES client(id) ON DELETE SET NULL
);

-- 3. DECODER
CREATE TABLE decoder (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         ip_address VARCHAR(20) NOT NULL UNIQUE,
                         client_id BIGINT,
                         CONSTRAINT fk_decoder_client FOREIGN KEY (client_id)
                             REFERENCES client(id) ON DELETE SET NULL
);

-- 4. CHANNEL
CREATE TABLE channel (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         name VARCHAR(100) NOT NULL
);

-- 5. DECODER_CHANNEL (relation many-to-many)
CREATE TABLE decoder_channel (
                                 decoder_id BIGINT NOT NULL,
                                 channel_id BIGINT NOT NULL,
                                 PRIMARY KEY (decoder_id, channel_id),
                                 CONSTRAINT fk_dc_decoder FOREIGN KEY (decoder_id)
                                     REFERENCES decoder(id) ON DELETE CASCADE,
                                 CONSTRAINT fk_dc_channel FOREIGN KEY (channel_id)
                                     REFERENCES channel(id) ON DELETE CASCADE
);

-- 🔐 Données par défaut

-- -- CLIENT par défaut
-- INSERT INTO client (name) VALUES ('Hotel Ibis');

-- UTILISATEURS par défaut
--   • admin / admin123
INSERT INTO `user` (username, password, role, client_id) VALUES
                                                             ('admin',
                                                              '$2a$10$Ly/HzeAs4WEHdUd3OXFF3OMoIjGYATF8NALZuhhHWJ.CBZBtgNyY2',
                                                              'ADMIN',
                                                              NULL);

-- CHAÎNES par défaut
INSERT INTO channel (name) VALUES
                               ('Netflix'),
                               ('HBO'),
                               ('ESPN'),
                               ('Disney+'),
                               ('National Geographic');

-- -- DÉCODEURS assignés à Hotel_Ibis
-- INSERT INTO decoder (ip_address, client_id) VALUES
--                                                 ('127.0.10.1', (SELECT id FROM client WHERE name = 'Hotel Ibis')),
--                                                 ('127.0.10.2', (SELECT id FROM client WHERE name = 'Hotel Ibis'));
