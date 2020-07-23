package ua.com.cyberneophyte.jumpic;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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
import ua.com.cyberneophyte.jumpic.repos.TheoryRepo;

import static org.hamcrest.Matchers.hasProperty;
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
public class TheoryControllerTest {
    private LinkedMultiValueMap<String, String> newTheoryParams = new LinkedMultiValueMap<>();
    @Autowired
    private MockMvc mockMvc;
    @Autowired
    private TheoryRepo theoryRepo;

    @BeforeEach
    public void initCourseFormWithCorrectValues() {
        newTheoryParams.add("title", "theory 1");
        newTheoryParams.add("content", "theory 1");
    }

    @AfterEach
    public void resetModuleForm() {
        newTheoryParams.clear();
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithCorrectDataTest() throws Exception {
        this.mockMvc.perform(post("/courseEditor/1/1/1/addTheoryLesson").params(newTheoryParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[3]").string("theory 1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[1]").string("1"));
        assertEquals(1, theoryRepo.findAll().size());
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithTooSmallTitleTest() throws Exception {
        newTheoryParams.remove("title");
        newTheoryParams.add("title", "1");
        this.mockMvc.perform(post("/courseEditor/1/1/1/addTheoryLesson").params(newTheoryParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p")
                        .string("Please enter title within range [3..64]"));
        assertEquals(0, theoryRepo.findAll().size());
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithTooLongTitleTest() throws Exception {
        newTheoryParams.remove("title");
        newTheoryParams.add("title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit pharetra.");
        this.mockMvc.perform(post("/courseEditor/1/1/1/addTheoryLesson").params(newTheoryParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p")
                        .string("Please enter title within range [3..64]"));
        assertEquals(0, theoryRepo.findAll().size());
    }

    @Test
    @WithUserDetails("testUser")
    public void createTheoryLessonWithTooShortContentTest() throws Exception {
        newTheoryParams.remove("content");
        newTheoryParams.add("content", "1");
        this.mockMvc.perform(post("/courseEditor/1/1/1/addTheoryLesson").params(newTheoryParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[2]/div/p")
                        .string("Please enter content for your theory lesson within range [3..2500]"));
        assertEquals(0, theoryRepo.findAll().size());
    }


    @ParameterizedTest
    @WithUserDetails("testUser")
    @CsvFileSource(resources = "/2501chars.csv", lineSeparator = "|", delimiter = '~')
    public void createTheoryLessonWithTooBigContentTest(String str) throws Exception {
        newTheoryParams.remove("content");
        newTheoryParams.add("content", str);
        this.mockMvc.perform(post("/courseEditor/1/1/1/addTheoryLesson").params(newTheoryParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[2]/div/p")
                        .string("Please enter content for your theory lesson within range [3..2500]"));
        assertEquals(0, theoryRepo.findAll().size());
    }

    @ParameterizedTest
    @WithUserDetails("testUser")
    @CsvFileSource(resources = "/2500chars.csv", lineSeparator = "|", delimiter = '~')
    public void createTheoryLessonWithBigContentWithinRangeTest(String str) throws Exception {
        newTheoryParams.remove("content");
        newTheoryParams.add("content", str);
        this.mockMvc.perform(post("/courseEditor/1/1/1/addTheoryLesson").params(newTheoryParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[3]").string("theory 1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[2]/div/div[1]").string("1"));
        assertEquals(1, theoryRepo.findAll().size());
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql", "/create-chapters.sql",
            "/create-theory-lessons.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void showEditFormForTheoryTest() throws Exception {
        this.mockMvc.perform(get("/courseEditor/1/1/1/edditTheory/1").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())

                .andExpect(model()
                        .attribute("theoryForm",hasProperty("title", Matchers.equalTo("title 1"))))
                .andExpect(model()
                        .attribute("theoryForm",hasProperty("content",
                                Matchers.equalTo("Lorem ipsum dolor sit amet, consectetur adipiscing elit viverra."))));
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql", "/create-chapters.sql",
            "/create-theory-lessons.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editTheoryLessonTest() throws Exception {
        newTheoryParams.clear();
        newTheoryParams.add("title","edited title");
        newTheoryParams.add("content","edited content");
        this.mockMvc.perform(post("/courseEditor/1/1/1/edditTheory/editTheoryLesson").params(newTheoryParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));


        assertTrue(theoryRepo.findTheoryById(1L).getContent().equals("edited content"));
        assertTrue(theoryRepo.findTheoryById(1L).getTitle().equals("edited title"));

    }

}
