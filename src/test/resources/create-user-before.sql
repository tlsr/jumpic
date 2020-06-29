delete from user_role;
delete from usr;

insert into usr(id, username, password, active,email,age) values
(1, 'testUser', '$2a$08$dQkI6qW7YCVe0PjLC43vTOO/6Yx0E5uuQ6xyOmHi1RTHK7XOgrTgm', true, 'test@test.com',18),
(2, 'admin', '$2a$08$dQkI6qW7YCVe0PjLC43vTOO/6Yx0E5uuQ6xyOmHi1RTHK7XOgrTgm', true,'admin@test.com',18);
insert into user_role(user_id, roles) values
(1, 'USER'),
(2, 'ADMIN'), (2, 'USER');