DELETE FROM user_roles;
DELETE FROM users;
DELETE FROM meals;
ALTER SEQUENCE global_seq RESTART WITH 100000;

INSERT INTO users (name, email, password) VALUES
  ('User', 'user@yandex.ru', 'password'),
  ('Admin', 'admin@gmail.com', 'admin');

INSERT INTO user_roles (role, user_id) VALUES
  ('ROLE_USER', 100000),
  ('ROLE_ADMIN', 100001);

INSERT INTO meals (date_time, description, calories, user_id) VALUES
  ('2019-06-20 09:15:00', 'завтрак', 500, 100000),
  ('2019-06-20 12:30:00', 'обед', 1000, 100000),
  ('2019-06-20 19:00:00', 'ужин', 800, 100000),
  ('2019-06-20 09:15:00', 'завтрак', 500, 100001),
  ('2019-06-20 12:30:00', 'обед', 500, 100001),
  ('2019-06-20 19:00:00', 'ужин', 800, 100001)