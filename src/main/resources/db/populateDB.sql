DELETE FROM meals;
DELETE FROM user_roles;
DELETE FROM users;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (user_id, datetime, description, calories) VALUES (100000, '2018-03-20 10:00:00', 'Завтрак', 500);
INSERT INTO meals (user_id, datetime, description, calories) VALUES (100000, '2018-03-20 13:00:00', 'Обед', 1000);
INSERT INTO meals (user_id, datetime, description, calories) VALUES (100000, '2018-03-20 20:00:00', 'Ужин', 500);
INSERT INTO meals (user_id, datetime, description, calories) VALUES (100001, '2018-03-21 10:00:00', 'Завтрак', 1000);
INSERT INTO meals (user_id, datetime, description, calories) VALUES (100001, '2018-03-21 13:00:00', 'Обед', 500);
INSERT INTO meals (user_id, datetime, description, calories) VALUES (100001, '2018-03-21 20:00:00', 'Ужин', 510);