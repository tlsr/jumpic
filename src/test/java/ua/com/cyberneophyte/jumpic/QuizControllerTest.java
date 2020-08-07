package ua.com.cyberneophyte.jumpic;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.params.ParameterizedTest;
import org.junit.jupiter.params.provider.CsvFileSource;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ua.com.cyberneophyte.jumpic.domain.Answer;
import ua.com.cyberneophyte.jumpic.domain.Quiz;
import ua.com.cyberneophyte.jumpic.repos.QuizRepo;
import ua.com.cyberneophyte.jumpic.repos.TheoryRepo;

import java.util.List;

import static org.hamcrest.Matchers.*;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql", "/create-chapters.sql"},
        executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class QuizControllerTest {
    private LinkedMultiValueMap<String, String> newQuizParams = new LinkedMultiValueMap<>();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private QuizRepo quizRepo;


    /*TODO
     *  validate
     *   +title 3..64
     *   +question text 3..2500
     *   +answer 1..64
     *   +at least 1 correct answer
     *   +points : negative, not a int number, 0 , bigger than 5
     *   +more than 10 answers
     *  +edit:
     *   +edit title, question, points, answers
     *  delete
     *   +quiz, answers
     *
     * */
    @BeforeEach
    public void initQuizFormWithCorrectValues() {
        newQuizParams.add("title", "quiz title");
        newQuizParams.add("question", "question text");
        newQuizParams.add("points", "2");
        newQuizParams.add("answers[0].answerText", "correct answer");
        newQuizParams.add("answers[0].isCorrect", "true");
    }

    @AfterEach
    public void resetQuizForm() {
        newQuizParams.clear();
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithCorrectDataTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[3]").string("quiz title"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[1]").string("1"));
        assertEquals(1, quizRepo.findAll().size());
        assertEquals("correct answer", quizRepo.findAll().get(0).getAnswers().get(0).getAnswerText());
        assertTrue(quizRepo.findAll().get(0).getAnswers().get(0).getIsCorrect());
    }

    @Test
    @WithUserDetails("testUser")
    public void createQuizLessonWithShortTitleTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("title", "");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p")
                        .string("Please enter title within range [3..64]"));

        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithLongTitleTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit eleifend.");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p")
                        .string("Please enter title within range [3..64]"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithShortQuestionTextTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("question", "");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[2]/div/p")
                        .string("Please enter text for your question within range [3..2500]"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @ParameterizedTest
    @WithUserDetails("testUser")
    @CsvFileSource(resources = "/2501chars.csv", lineSeparator = "|", delimiter = '~')
    public void createTheoryLessonWithTooLongQuestionTextTest(String question) throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("question", question);
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[2]/div/p")
                        .string("Please enter text for your question within range [3..2500]"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @ParameterizedTest
    @WithUserDetails("testUser")
    @CsvFileSource(resources = "/2500chars.csv", lineSeparator = "|", delimiter = '~')
    public void createTheoryLessonWithLongQuestionTextTest(String question) throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("question", question);
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[3]").string("quiz title"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[1]").string("1"));
        assertEquals(1, quizRepo.findAll().size());
        assertEquals("correct answer", quizRepo.findAll().get(0).getAnswers().get(0).getAnswerText());
        assertEquals(question, quizRepo.findAll().get(0).getQuestion());
        assertTrue(quizRepo.findAll().get(0).getAnswers().get(0).getIsCorrect());
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithShortAnswerTextTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("answers[0].answerText", "");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[4]/div/div[1]/div/p")
                        .string("Please keep your answer text within range[1..64]"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithLongAnswerTextTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("answers[0].answerText", "Lorem ipsum dolor sit amet, consectetur adipiscing elit eleifend.");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[4]/div/div[1]/div/p")
                        .string("Please keep your answer text within range[1..64]"));
        assertTrue(quizRepo.findAll().isEmpty());
    }


    @Test
    @WithUserDetails("testUser")
    public void createQuizLessonWithnoCorrectAnswersTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("answers[0].isCorrect", "false");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[5]/div[2]/p")
                        .string("Question should contain at least 1 correct answer"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createQuizLessonWithNegativePointsTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("points", "-1");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[3]/div/p")
                        .string("must be greater than or equal to 1"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createQuizLessonWithPointsAmountBiggerThan5Test() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("points", "6");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[3]/div/p")
                        .string("must be less than or equal to 5"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createQuizLessonWithPointsNotANumberTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("points", "gfsdgsdfs");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[3]/div/p")
                        .string("Please enter a correct data"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createQuizLessonWithMoreThanTenAnswersTest() throws Exception {
        newQuizParams.add("saveQuiz", "");
        newQuizParams.set("answers[0].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[0].isCorrect", "true");
        newQuizParams.set("answers[1].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[1].isCorrect", "false");
        newQuizParams.set("answers[2].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[2].isCorrect", "false");
        newQuizParams.set("answers[3].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[3].isCorrect", "false");
        newQuizParams.set("answers[4].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[4].isCorrect", "false");
        newQuizParams.set("answers[5].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[5].isCorrect", "false");
        newQuizParams.set("answers[6].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[6].isCorrect", "false");
        newQuizParams.set("answers[7].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[7].isCorrect", "false");
        newQuizParams.set("answers[8].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[8].isCorrect", "false");
        newQuizParams.set("answers[9].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[9].isCorrect", "false");
        newQuizParams.set("answers[10].answerText", "Lorem ipsum dolor sit amet.");
        newQuizParams.set("answers[10].isCorrect", "false");
        this.mockMvc.perform(post("/courseEditor/1/1/1/quizEditor").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[15]/div[2]/p")
                        .string("Question can have no more than 10 answers"));
        assertTrue(quizRepo.findAll().isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql", "/create-chapters.sql",
            "/create-quiz-lessons.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showEditFormForQuizTest() throws Exception {
        this.mockMvc.perform(get("/courseEditor/1/1/1/1/editLesson/").with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1/1/1/1/editQuiz"));
        this.mockMvc.perform(get("/courseEditor/1/1/1/1/editQuiz").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(model()
                        .attribute("quizForm", hasProperty("title", Matchers.equalTo("title 1"))))
                .andExpect(model()
                        .attribute("quizForm", hasProperty("question",
                                Matchers.equalTo("question text 1"))))
                .andExpect(model()
                        .attribute("quizForm", hasProperty("points",
                                Matchers.equalTo(1))))
                .andExpect(model()
                        .attribute("quizForm", hasProperty("answers",
                                hasItem(
                                        allOf(
                                                hasProperty("answerText",is("answer text 1")),
                                                hasProperty("isCorrect",is(true))
                                        )
                                ))))
                .andExpect(model().attribute("quizForm",
                        hasProperty("answers", hasSize(4))
                ));
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql", "/create-chapters.sql",
            "/create-quiz-lessons.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editQuizInChapterTest() throws Exception {
        newQuizParams.set("title","edited title");
        newQuizParams.add("id","1");
        newQuizParams.set("question", "edited question text");
        newQuizParams.set("points", "3");
        newQuizParams.set("answers[0].answerText", "edited correct answer");
        newQuizParams.set("answers[0].isCorrect", "true");
        newQuizParams.add("saveQuiz", "");
        this.mockMvc.perform(post("/courseEditor/1/1/1/1/editQuizLesson").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[3]").string("edited title"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[1]").string("1"));
        assertEquals(4, quizRepo.findAll().size());
        assertEquals("edited correct answer", quizRepo.findAll().get(0).getAnswers().get(0).getAnswerText());
        assertTrue(quizRepo.findAll().get(0).getAnswers().get(0).getIsCorrect());
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql", "/create-chapters.sql",
            "/create-quiz-lessons.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteQuizTest() throws Exception{
        System.out.println();
        this.mockMvc.perform(post("/courseEditor/1/1/1/1/deleteLesson").with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[3]").string("title 2"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[1]").string("1"));
        assertEquals(3, quizRepo.findAll().size());
        assertEquals("answer text 1", quizRepo.findAll().get(0).getAnswers().get(0).getAnswerText());
    }

    @Test
    @Disabled
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql", "/create-chapters.sql",
            "/create-quiz-lessons.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteAnswersInQuizTest() throws Exception{
        newQuizParams.add("removeRow", "1");
        this.mockMvc.perform(post("/courseEditor/1/1/1/1/editQuizLesson").params(newQuizParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
        /*.andExpect(xpath())*/;
        assertEquals(4, quizRepo.findAll().size());
        assertEquals("edited correct answer", quizRepo.findAll().get(0).getAnswers().get(0).getAnswerText());
        assertTrue(quizRepo.findAll().get(0).getAnswers().get(0).getIsCorrect());
    }
}
