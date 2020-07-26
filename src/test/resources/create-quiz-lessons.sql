INSERT INTO lesson(id, consecutive_number, title, chapter_id)
VALUES (1,1,"title 1",1);
INSERT INTO quiz(question, lesson_id, points)
VALUES("question text 1",1,1);
INSERT INTO answer(id, answer_text, is_correct, quiz_id)
VALUES (1,"answer text 1",true,1);
INSERT INTO answer(id, answer_text, is_correct, quiz_id)
VALUES (2,"answer text 2",false,1);
INSERT INTO answer(id, answer_text, is_correct, quiz_id)
VALUES (3,"answer text 3",false,1);
INSERT INTO answer(id, answer_text, is_correct, quiz_id)
VALUES (4,"answer text 4",false,1);

INSERT INTO lesson(id, consecutive_number, title, chapter_id)
VALUES (2,2,"title 2",1);
INSERT INTO quiz(question, lesson_id, points)
VALUES("question text 2",2,1);
INSERT INTO answer(id, answer_text, is_correct, quiz_id)
VALUES (5,"answer text 1",true,2);

INSERT INTO lesson(id, consecutive_number, title, chapter_id)
VALUES (3,3,"title 3",1);
INSERT INTO quiz(question, lesson_id, points)
VALUES("question text 3",3,1);
INSERT INTO answer(id, answer_text, is_correct, quiz_id)
VALUES (6,"answer text 1",true,3);

INSERT INTO lesson(id, consecutive_number, title, chapter_id)
VALUES (4,4,"title 4",1);
INSERT INTO quiz(question, lesson_id, points)
VALUES("question text 4",4,1);
INSERT INTO answer(id, answer_text, is_correct, quiz_id)
VALUES (7,"answer text 1",true,3);

