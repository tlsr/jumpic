
delete  from answer;
delete from theory;
delete from quiz;
delete  from lesson;
delete from chapters;
delete from course_tags;
delete from modules;
delete from courses;
delete from course_info;
delete from user_role;
delete from usr;
INSERT INTO hibernate_sequence (next_val) VALUES (1);
alter table  answer AUTO_INCREMENT=1;
alter table  theory AUTO_INCREMENT=1;
alter table  quiz AUTO_INCREMENT=1;
alter table  lesson AUTO_INCREMENT=1;
alter table  chapters AUTO_INCREMENT=1;
alter table  modules AUTO_INCREMENT=1;
ALTER TABLE course_tags AUTO_INCREMENT=1;
ALTER TABLE courses AUTO_INCREMENT=1;
ALTER TABLE course_info AUTO_INCREMENT=1;
ALTER TABLE user_role AUTO_INCREMENT=1;
ALTER TABLE usr AUTO_INCREMENT=1;