DROP TABLE IF EXISTS user;
DROP TABLE IF EXISTS decoder;
DROP TABLE IF EXISTS client;

CREATE TABLE client (
                        id BIGINT AUTO_INCREMENT PRIMARY KEY,
                        name VARCHAR(255) NOT NULL
);

CREATE TABLE user (
                      id BIGINT AUTO_INCREMENT PRIMARY KEY,
                      username VARCHAR(255) NOT NULL UNIQUE,
                      password VARCHAR(255) NOT NULL,
                      role VARCHAR(20) NOT NULL,
                      client_id BIGINT,
                      CONSTRAINT fk_user_client FOREIGN KEY (client_id) REFERENCES client(id)
);

CREATE TABLE decoder (
                         id BIGINT AUTO_INCREMENT PRIMARY KEY,
                         ip_address VARCHAR(50) NOT NULL,
                         client_id BIGINT,
                         CONSTRAINT fk_decoder_client FOREIGN KEY (client_id) REFERENCES client(id)
);

INSERT INTO client (name) VALUES
                              ('Hotel Québec'),
                              ('Université UQTR');

INSERT INTO user (username, password, role, client_id) VALUES
                                                           ('admin', '$2a$10$6UxHyqZ.qaxHTKKlViUXUOeYtrqbv1BqTfZcE6TjM5V4iVa/8FqXq', 'ADMIN', NULL),
                                                           ('user1', '$2a$10$XwLssDB79ZNmipB0E3XxVOcKrcp5n8hYdKNdFL95IslFh7rhGZ0Lu', 'CLIENT', 1),
                                                           ('user2', '$2a$10$XwLssDB79ZNmipB0E3XxVOcKrcp5n8hYdKNdFL95IslFh7rhGZ0Lu', 'CLIENT', 2);

-- Les mots de passe pour user1 et user2 = "test123"
-- Le mot de passe pour admin = "admin123"

INSERT INTO decoder (ip_address, client_id) VALUES
                                                ('127.0.10.1', 1),
                                                ('127.0.10.2', 1),
                                                ('127.0.10.3', 2);
