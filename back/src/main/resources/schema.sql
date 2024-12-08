DROP TABLE IF EXISTS pix;
DROP TABLE IF EXISTS users;

CREATE TABLE users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(255),
    email VARCHAR(255)
);

CREATE TABLE pix (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pix_key VARCHAR(255) NOT NULL,
    amount DECIMAL(10,2) NOT NULL,
    description VARCHAR(500),
    status VARCHAR(20) NOT NULL,
    qr_code_text CLOB,
    qr_code_image CLOB,
    created_at TIMESTAMP NOT NULL,
    confirmed_at TIMESTAMP,
    user_id BIGINT,
    city VARCHAR(255),
    state VARCHAR(255),
    FOREIGN KEY (user_id) REFERENCES users(id)
);

CREATE TABLE qr_codes (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    text TEXT,
    image TEXT
);