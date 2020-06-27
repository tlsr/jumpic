create table course_tags
(
    course_info_id bigint not null,
    tag            varchar(255)

) engine=InnoDB;
create table course_info
(
    id                       bigint not null auto_increment,
    description              longtext,
    estimated_time_to_finish integer,
    points                   integer,
    title                    varchar(255),
    user_id                  bigint,
    primary key (id)
) engine=InnoDB;
create table courses
(
    id            bigint not null auto_increment,
    courseinfo_id bigint,
    primary key (id)
) engine=InnoDB;
create table hibernate_sequence
(
    next_val bigint
) engine=InnoDB;
insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);
insert into hibernate_sequence
values (1);
create table quiz
(
    id       integer not null auto_increment,
    question varchar(255),
    primary key (id)
) engine=InnoDB;
create table user_role
(
    user_id bigint not null ,
    roles   varchar(255)
) engine=InnoDB;
create table usr
(
    id       bigint not null auto_increment,
    active   bit    not null,
    age      integer,
    email    varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
) engine=InnoDB;
alter table course_tags
    add constraint course_info_tags_fk foreign key (course_info_id) references course_info (id);
alter table course_info
    add constraint courseinfo_author_fk foreign key (user_id) references usr (id);
alter table courses
    add constraint course_courseinfo_fk foreign key (courseinfo_id) references course_info (id);
alter table user_role
    add constraint user_user_role_fk foreign key (user_id) references usr (id);
