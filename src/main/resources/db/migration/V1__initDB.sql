create table answer
(
    id          bigint not null auto_increment,
    answer_text varchar(255),
    is_correct  bit,
    quiz_id     bigint,
    primary key (id)
) engine = InnoDB;
create table chapters
(
    id                 bigint not null auto_increment,
    chapter_name       varchar(255),
    consecutive_number integer,
    module_id          bigint,
    primary key (id)
) engine = InnoDB;
create table course_tags
(
    course_info_id bigint not null,
    tag            varchar(255)
) engine = InnoDB;
create table course_info
(
    id                       bigint not null auto_increment,
    description              longtext,
    estimated_time_to_finish integer,
    points                   integer,
    title                    varchar(255),
    user_id                  bigint,
    primary key (id)
) engine = InnoDB;
create table courses
(
    id            bigint not null auto_increment,
    courseinfo_id bigint,
    primary key (id)
) engine = InnoDB;
create table hibernate_sequence
(
    next_val bigint
) engine = InnoDB;
insert into hibernate_sequence values ( 1 );
create table lesson
(
    id                 bigint not null,
    consecutive_number integer,
    title              varchar(255),
    chapter_id         bigint,
    primary key (id)
) engine = InnoDB;
create table modules
(
    id                 bigint not null,
    consecutive_number integer,
    module_name        varchar(255),
    points             integer,
    course_id          bigint,
    primary key (id)
) engine = InnoDB;
create table quiz
(
    points    integer,
    question  longtext,
    lesson_id bigint not null,
    primary key (lesson_id)
) engine = InnoDB;
create table theory
(
    content   longtext,
    lesson_id bigint not null,
    primary key (lesson_id)
) engine = InnoDB;
create table user_role
(
    user_id bigint not null,
    roles   varchar(255)
) engine = InnoDB;
create table usr
(
    id       bigint not null auto_increment,
    active   bit    not null,
    age      integer,
    email    varchar(255),
    password varchar(255),
    username varchar(255),
    primary key (id)
) engine = InnoDB;
alter table answer
    add constraint FK_answers_quiz foreign key (quiz_id) references quiz (lesson_id);
alter table chapters
    add constraint FK_chapters_module foreign key (module_id) references modules (id);
alter table course_tags
    add constraint FK_course_tags_course_infi foreign key (course_info_id) references course_info (id);
alter table course_info
    add constraint FK_course_info_author foreign key (user_id) references usr (id);
alter table courses
    add constraint FK_course_course_info foreign key (courseinfo_id) references course_info (id);
alter table lesson
    add constraint FK_lessons_chapters foreign key (chapter_id) references chapters (id);
alter table modules
    add constraint FK_modules_courses foreign key (course_id) references courses (id);
alter table quiz
    add constraint FK_quiz_lesson foreign key (lesson_id) references lesson (id);
alter table theory
    add constraint FK_theory_lesson foreign key (lesson_id) references lesson (id);
alter table user_role
    add constraint FK_user_role_usr foreign key (user_id) references usr (id);