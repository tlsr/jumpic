package ua.com.cyberneophyte.jumpic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.repos.CourseRepo;
import ua.com.cyberneophyte.jumpic.service.CourseService;

import java.util.List;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/init-db-for-create-course-testing.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/drop-down-db-after-course-creation-tests.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class CourseInfoEditorControllerTest {

    @Autowired
    MockMvc mockMvc;
    @Autowired
    CourseRepo courseRepo;

    private LinkedMultiValueMap<String, String> newCourseParams = new LinkedMultiValueMap<>();

    @BeforeEach
    public void initCourseFormWithCorrectValues() {
        newCourseParams.add("title", "simple title");
        newCourseParams.add("tags", "java, css, javascript");
        newCourseParams.add("description", "simple description");
        newCourseParams.add("estimatedTimeToFinish", "12");
    }

    @AfterEach
    public void resetCourseForm() {
        newCourseParams.clear();
    }


    @Test
    public void createCourseWithoutAuthTest() throws Exception {
        this.mockMvc.perform(get("/courseInfoEditor"))
                .andDo(print())
                .andExpect(status().is3xxRedirection())
                .andExpect(redirectedUrl("http://localhost/login"));
    }

    @Test
    @WithUserDetails("testUser")
    public void accessToCourseEditorWithAuthUserTest() throws Exception {
        this.mockMvc.perform(get("/courseInfoEditor"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    @WithUserDetails("testUser")
    @Disabled
    public void createCourseInfoWithCorrectData() throws Exception {
        this.mockMvc.perform(post("/courseInfoEditor").params(newCourseParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseView/1"));
        this.mockMvc.perform(get("/courseView/1"))
                .andDo(print())
                .andExpect(xpath("/html/body/div[1]").string("simple title"))
                .andExpect(xpath("/html/body/div[2]").string("testUser"))
                .andExpect(xpath("/html/body/div[3]").string("simple description"))
                .andExpect(xpath("/html/body/div[4]").string("12"))
                .andExpect(xpath("/html/body/div[5]/span").nodeCount(3));
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/div/div").nodeCount(1))
                .andExpect(xpath("/html/body/div/div/div/div/h5").string("simple title"))
                .andExpect(xpath("/html/body/div/div/div/div/div[1]").string("testUser"))
                .andExpect(xpath("/html/body/div/div/div/div/pre/div").string("simple description"))
                .andExpect(xpath("/html/body/div/div/div/div/span").nodeCount(3));
        List<Course> list = courseRepo.findAll();
        assertTrue(list.size() == 1);
    }


    @Test
    @WithUserDetails("testUser")
    public void createCourseWithEmptyFields() throws Exception {
        newCourseParams.clear();
        newCourseParams.add("title", "a");
        newCourseParams.add("tags", "");
        newCourseParams.add("description", "");
        newCourseParams.add("estimatedTimeToFinish", "0");
        this.mockMvc.perform(post("/courseInfoEditor").params(newCourseParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/form/div/div[1]/p").string("size must be between 6 and 255"))
                .andExpect(xpath("/html/body/form/div/div[4]/p").string("must be greater than or equal to 1"));
        List<Course> list = courseRepo.findAll();
        assertTrue(list.isEmpty());
    }

}
