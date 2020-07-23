package ua.com.cyberneophyte.jumpic;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
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

import java.util.List;

import static org.hamcrest.Matchers.containsString;
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
    public void createCourseInfoWithCorrectData() throws Exception {
        this.mockMvc.perform(post("/courseInfoEditor").params(newCourseParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseView/1"));
        this.mockMvc.perform(get("/courseView/1"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/h1/div").string("simple title"))
                .andExpect(xpath("/html/body/div/div[1]/span[2]").string("testUser"))
                .andExpect(xpath("/html/body/div/pre/div").string("simple description"))
                .andExpect(xpath("/html/body/div/div[3]/span[2]").string("12"))
                .andExpect(xpath("/html/body/div/div[4]/span").nodeCount(4));
        this.mockMvc.perform(get("/"))
                .andDo(print())
                .andExpect(xpath("/html/body/div/div/div").nodeCount(1))
                .andExpect(xpath("/html/body/div/div/div/div/h5").string("simple title"))
                .andExpect(xpath("/html/body/div/div/div/div/div[1]").string("testUser"))
                .andExpect(xpath("/html/body/div/div[1]/div/div/pre").string("simple description"))
                .andExpect(xpath("/html/body/div/div/div/div/span").nodeCount(3));
        List<Course> list = courseRepo.findAll();
        assertEquals(1, list.size());
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
                .andExpect(xpath("/html/body/form/div/div[1]/p")
                        .string("Please choose title within range[3,64]"))//title
                .andExpect(xpath("/html/body/form/div/div[4]/p")
                        .string("Please give your students at least 1 hour to finish course"))//estimated time
                .andExpect(xpath("/html/body/form/div/div[2]")
                        .string(containsString("Please add some tags")))//tags
                .andExpect(xpath("/html/body/form/div/div[3]")
                        .string(containsString("Please give some description with size of [10...500]")))//description
        ;
        List<Course> list = courseRepo.findAll();
        assertTrue(list.isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createCourseWhitTooBigTitle() throws Exception {
        newCourseParams.remove("title");
        newCourseParams.add("title", "Lorem ipsum dolor sit amet, consectetur adipiscing elit eleifend.");
        this.mockMvc.perform(post("/courseInfoEditor").params(newCourseParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/form/div/div[1]/p")
                        .string("Please choose title within range[3,64]"));//title
        List<Course> list = courseRepo.findAll();
        assertTrue(list.isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    public void createCourseWithTooBigDescription() throws Exception {
        newCourseParams.remove("description");
        newCourseParams.add("description", "Lorem ipsum dolor sit amet, consectetur adipiscing elit. Maecenas ultrices" +
                " dignissim purus, ut tincidunt felis hendrerit vel. Nulla neque nibh, aliquam quis quam at, placerat " +
                "rutrum nulla. Mauris ut ipsum eu nunc sodales egestas. Integer imperdiet nisl in maximus pretium. " +
                "Quisque pretium ipsum id elit tempus, euismod ornare dui porta. Aenean sed tortor ut risus maximus " +
                "vestibulum. In eget erat nisi. Donec nibh nibh, maximus sit amet fermentum et, posuere vel lectus. " +
                "Suspendisse sagittis accumsan.");
        this.mockMvc.perform(post("/courseInfoEditor").params(newCourseParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/form/div/div[3]")
                        .string(containsString("Please give some description with size of [10...500]")));//title
        List<Course> list = courseRepo.findAll();
        assertTrue(list.isEmpty());
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/init-db-for-create-course-testing.sql","/create-course.sql"},executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void createCourseWithExistingTitle() throws Exception{
        newCourseParams.remove("title");
        newCourseParams.add("title", "simpleTitle");
        this.mockMvc.perform(post("/courseInfoEditor").params(newCourseParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/form/div/div[1]/p")
                        .string("Course with this title already exist"));
        List<Course> list = courseRepo.findAll();
        assertEquals(1, list.size());
    }

}
