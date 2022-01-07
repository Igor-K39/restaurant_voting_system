-- noinspection SqlWithoutWhereForFile

DELETE FROM vote;
DELETE FROM menu_item;
DELETE FROM dish;
DELETE FROM restaurant;
DELETE FROM users;
DELETE FROM user_roles;
ALTER SEQUENCE GLOBAL_SEQ RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user.sd@gmail.com', '$2a$10$Xg5LHE.UX/3kOM2wGn/8cegWuSovUfaw2Y.9Tx7k8nRbf0SvycvYW'),   -- 100_000
       ('Admin', 'admin.sd@gmail.com', '$2a$10$I8BSO9meeinTKz5pXvqi4uK2nncdU4Krj/PscmAjgKzYgFFhFlCHi'); -- 100_001

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

INSERT INTO menu(restaurant_id, name, date_of)
VALUES (100002, 'Меню 1', '2021-03-01'), -- 100_010
       (100002, 'Меню 2', '2021-03-02'), -- 100_011
       (100003, 'Меню 3', '2021-03-01'), -- 100_012
       (100003, 'Меню 4', '2021-03-02'); -- 100_013

INSERT INTO menu_item (menu_id, dish_item_id, price)
VALUES (100010, 100004, 13000),                 -- 100_014 R1
       (100010, 100005, 30000),                 -- 100_015 R1
       (100011, 100005, 32000),                 -- 100_016 R1
       (100011, 100006, 19000),                 -- 100_017 R1
       (100012, 100007, 10000),                 -- 100_018 R2
       (100012, 100008, 12000),                 -- 100_019 R2
       (100013, 100008, 17000),                 -- 100_020 R2
       (100013, 100009, 11000);                 -- 100_021 R2

INSERT INTO vote(user_id, restaurant_id, date_of, time_of)
VALUES (100000, 100002, '2021-03-01', '09:00:00'),            -- 100_022
       (100001, 100002, '2021-03-01', '09:00:00'),            -- 100_023
       (100000, 100003, '2021-03-02', '10:00:00'),            -- 100_024
       (100001, 100003, '2021-03-02', '10:00:00');            -- 100_025