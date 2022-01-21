DROP TABLE IF EXISTS vote, menu_item, menu, dish, restaurant, user_roles, users;
DROP SEQUENCE IF EXISTS GLOBAL_SEQ;

CREATE SEQUENCE GLOBAL_SEQ START WITH 100000;

CREATE TABLE users
(
    id         BIGINT    DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    name       VARCHAR(80)             NOT NULL,
    email      VARCHAR(50)             NOT NULL,
    password   VARCHAR(255)            NOT NULL,
    enabled    BOOLEAN   DEFAULT TRUE  NOT NULL,
    registered TIMESTAMP DEFAULT NOW() NOT NULL
);
CREATE UNIQUE INDEX users_unique_email_idx ON users (email);

CREATE TABLE user_roles
(
    user_id INTEGER      NOT NULL,
    role    VARCHAR(120) NOT NULL,
    CONSTRAINT user_roles_idx UNIQUE (user_id, role),
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE
);

CREATE TABLE restaurant
(
    id      BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    name    VARCHAR(80)  NOT NULL,
    website VARCHAR(80)  NOT NULL,
    address VARCHAR(120) NOT NULL,
    CONSTRAINT restaurant_unique_name_address_idx UNIQUE (name, address)
);

CREATE TABLE dish
(
    id            BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    name          VARCHAR(80) NOT NULL,
    restaurant_id INTEGER     NOT NULL,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    CONSTRAINT name_restaurant_idx UNIQUE (name, restaurant_id)
);

CREATE TABLE menu
(
    id            BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    restaurant_id INTEGER NOT NULL,
    name          VARCHAR(80) NOT NULL,
    date_of       DATE   DEFAULT CURRENT_DATE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE
);

CREATE TABLE menu_item
(
    id            BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    menu_id       INTEGER,
    dish_item_id  INTEGER NOT NULL,
    price         INTEGER NOT NULL,
    FOREIGN KEY (dish_item_id) REFERENCES dish (id) ON DELETE CASCADE,
    FOREIGN KEY (menu_id) REFERENCES menu(id) ON DELETE CASCADE
);

CREATE TABLE vote
(
    id            BIGINT DEFAULT NEXT VALUE FOR GLOBAL_SEQ PRIMARY KEY,
    user_id       INTEGER NOT NULL,
    restaurant_id INTEGER NOT NULL,
    date_of       DATE   DEFAULT CURRENT_DATE,
    time_of       TIME   DEFAULT CURRENT_TIME,
    FOREIGN KEY (user_id) REFERENCES users (id) ON DELETE CASCADE,
    FOREIGN KEY (restaurant_id) REFERENCES restaurant (id) ON DELETE CASCADE,
    CONSTRAINT user_date_idx UNIQUE (user_id, date_of)
)