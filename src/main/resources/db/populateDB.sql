DELETE FROM vote;
DELETE FROM menu_item;
DELETE FROM dish;
DELETE FROM restaurant;
DELETE FROM users;
DELETE FROM user_roles;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user.sd@gmail.com', '{noop}user_password'),
       ('Admin', 'admin.sd@gmail.com', '{noop}admin_password');

INSERT INTO user_roles (user_id, role)
VALUES (100000, 'USER'),
       (100001, 'ADMIN');

INSERT INTO restaurant (name, website, address)
VALUES ('Restaurant', 'restaurant.com', 'Minsk'),               -- 100_002
       ('The restaurant 2', 'WannaBeRestaurant.com', 'Minsk');  -- 100_003

INSERT INTO dish (name, restaurant_id)
VALUES ('R1-Borsch',    100002),                       -- 100_004
       ('R1-Beef',      100002),                       -- 100_005
       ('R1-Pork',      100002),                       -- 100_006
       ('R2-Potato',    100003),                       -- 100_007
       ('R2-Mushrooms', 100003),                       -- 100_008
       ('R2-Cabbage',   100003);                       -- 100_009

INSERT INTO menu_item (dish_item_id, restaurant_id, date_of, price)
VALUES (100004, 100002, '2021-03-01', 13000),                 -- 100_010 R1
       (100005, 100002, '2021-03-01', 30000),                 -- 100_011 R1
       (100006, 100002, '2021-03-02', 32000),                 -- 100_012 R1
       (100007, 100003, '2021-03-01', 10000),                 -- 100_013 R2
       (100008, 100003, '2021-03-01', 17000),                 -- 100_014 R2
       (100009, 100003, '2021-03-02', 11000);                 -- 100_015 R2

INSERT INTO vote(user_id, restaurant_id, date_of, time_of)
VALUES (100000, 100002, '2021-03-01', '09:00:00'),            -- 100_016
       (100001, 100002, '2021-03-01', '09:00:00'),            -- 100_017
       (100000, 100003, '2021-03-02', '10:00:00'),            -- 100_018
       (100001, 100003, '2021-03-02', '10:00:00');            -- 100_019