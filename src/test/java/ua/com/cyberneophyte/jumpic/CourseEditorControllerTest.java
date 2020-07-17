package ua.com.cyberneophyte.jumpic;

import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import ua.com.cyberneophyte.jumpic.domain.*;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.repos.CourseRepo;
import ua.com.cyberneophyte.jumpic.repos.LessonRepo;
import ua.com.cyberneophyte.jumpic.repos.TheoryRepo;

import java.util.HashMap;
import java.util.List;
import java.util.Map;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
//@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
//@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CourseEditorControllerTest {

    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private LessonRepo lessonRepo;
    @Autowired
    private CourseRepo courseRepo;
    @Autowired
    private ChapterRepo chapterRepo;
    @Autowired
    private TheoryRepo theoryRepo;

    @Test
    public void simpleTest() throws Exception {
/* *//*       Course course = courseRepo.findAll().get(0);
        Chapter chapter = chapterRepo.findAll().get(0);
        Quiz quiz = new Quiz();
        quiz.setChapter(chapter);
        quiz.setTitle("simple title from test");
        quiz.setQuestion("to be or not to be");
        Map<String,Boolean> answ = new HashMap<>();
        answ.put("correct answer", true);
        answ.put("incorrect answer2", false);
        answ.put("incorrect answer3", false);
        answ.put("incorrect answe4r", false);
        answ.put("incorrect answer5", false);
        quiz.setAnswers(answ);
        lessonRepo.save(quiz);*//*
        Theory theory = (Theory) lessonRepo.findLessonById(4L);
        System.out.println(theory);*/
    }
}
