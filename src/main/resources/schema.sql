-- Reference schema (JPA auto-creates tables; kept here for documentation)

CREATE TABLE IF NOT EXISTS users (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(100) NOT NULL,
    mobile_no VARCHAR(20) NOT NULL UNIQUE,
    password VARCHAR(255) NOT NULL,
    address VARCHAR(255),
    role VARCHAR(20) NOT NULL
);

CREATE TABLE IF NOT EXISTS mess (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    name VARCHAR(150) NOT NULL,
    address VARCHAR(255),
    owner_id BIGINT NOT NULL,
    CONSTRAINT fk_mess_owner FOREIGN KEY (owner_id) REFERENCES users(id)
);

CREATE TABLE IF NOT EXISTS menu (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    mess_id BIGINT NOT NULL,
    combo_name VARCHAR(150) NOT NULL,
    items VARCHAR(500),
    price DOUBLE NOT NULL,
    CONSTRAINT fk_menu_mess FOREIGN KEY (mess_id) REFERENCES mess(id)
);

CREATE TABLE IF NOT EXISTS orders (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    mess_id BIGINT NOT NULL,
    combo_id BIGINT NOT NULL,
    quantity INT NOT NULL,
    total_price DOUBLE NOT NULL,
    status VARCHAR(30) NOT NULL,
    otp VARCHAR(4),
    created_at DATETIME,
    CONSTRAINT fk_order_user FOREIGN KEY (user_id) REFERENCES users(id),
    CONSTRAINT fk_order_mess FOREIGN KEY (mess_id) REFERENCES mess(id),
    CONSTRAINT fk_order_combo FOREIGN KEY (combo_id) REFERENCES menu(id)
);

CREATE TABLE IF NOT EXISTS order_history (
    id BIGINT AUTO_INCREMENT PRIMARY KEY,
    order_id BIGINT NOT NULL,
    status VARCHAR(30) NOT NULL,
    completed_at DATETIME,
    CONSTRAINT fk_history_order FOREIGN KEY (order_id) REFERENCES orders(id)
);
