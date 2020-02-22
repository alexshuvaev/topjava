DELETE
FROM meals;
DELETE
FROM user_roles;
DELETE
FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password)
VALUES ('User', 'user@yandex.ru', 'password'),
       ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id)
VALUES ('ROLE_USER', 100000),
       ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id)
VALUES ('2020-01-30 07:30:00', 'Завтрак', 500, 100000),
       ('2020-01-30 13:00:00', 'Обед', 750, 100000),
       ('2020-01-30 19:00:00', 'Ужин', 700, 100000),

       ('2020-01-31 07:00:00', 'Завтрак', 300, 100000),
       ('2020-01-31 12:30:00', 'Обед', 900, 100000),
       ('2020-01-31 18:30:00', 'Ужин', 700, 100000),

       ('2020-01-30 07:00:00', 'Ранний завтрак', 550, 100001),
       ('2020-01-30 12:30:00', 'Обед', 900, 100001),
       ('2020-01-30 19:00:00', 'Ужин', 400, 100001),
       ('2020-01-30 22:30:00', 'Поздний ужин', 350, 100001),

       ('2020-01-31 10:00:00', 'Поздний завтрак', 800, 100001),
       ('2020-01-31 20:00:00', 'Ужин', 900, 100001);
