CREATE TABLE pix (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    pix_key VARCHAR(255) NOT NULL,
    pix_value DECIMAL(10, 2) NOT NULL,
    name VARCHAR(255),
    status VARCHAR(50),
    qr_code_text VARCHAR(2048),
    qr_code_image VARCHAR(2048),
    created_at TIMESTAMP,
    confirmed_at TIMESTAMP,
    city VARCHAR(100),
    state VARCHAR(100)
);