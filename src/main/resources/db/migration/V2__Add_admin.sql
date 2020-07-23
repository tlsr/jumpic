insert into usr (id, active,age,email,password,username)
values (1, 1, 12, 'dsa@dsa.com', '$2a$08$6EJ2B5BksB40uREgd1B.feUXReta/792BQoCE3/f78zTMUI1gW1Fe', 'admin');

insert into user_role(user_id,roles)
values (1,'USER'),(1,'ADMIN');
