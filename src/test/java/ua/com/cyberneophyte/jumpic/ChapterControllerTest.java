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
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.redirectedUrl;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-module.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ChapterControllerTest {
    private LinkedMultiValueMap<String, String> newChapterParams = new LinkedMultiValueMap<>();
    @Autowired
    MockMvc mockMvc;
    @Autowired
    ChapterRepo chapterRepo;

    @BeforeEach
    public void initCourseFormWithCorrectValues() {
        newChapterParams.add("chapterName", "chapter 1");
    }

    @AfterEach
    public void resetModuleForm() {
        newChapterParams.clear();
    }

    @Test
    @WithUserDetails("testUser")
    public void createModuleWithCorrectNameTest() throws Exception {
        this.mockMvc.perform(post("/courseEditor/1/1/addChapter").params(newChapterParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[2]").string("chapter 1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[1]").string("1"));
        assertEquals(1, chapterRepo.findAll().size());
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-modules.sql", "/create-chapters.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editChapterNameTest() throws Exception {
        newChapterParams.remove("chapterName");
        newChapterParams.add("chapterName", "edited chapter 1");
        this.mockMvc.perform(post("/courseEditor/1/1/editChapter/1").params(newChapterParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[2]").string("edited chapter 1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[1]").string("1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[5]/div[1]/div[2]").string("chapter 4"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[5]/div[1]/div[1]").string("4"));
        assertEquals(6, chapterRepo.findAll().size());

    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-modules.sql", "/create-chapters.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteChapterTest() throws Exception {
        this.mockMvc.perform(post("/courseEditor/1/1/deleteChapter/1").with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[2]").string("chapter 2"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[2]/div[1]/div[1]").string("1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[5]/div[1]/div[2]").string("chapter 5"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[5]/div[1]/div[1]").string("4"));
        assertEquals(5, chapterRepo.findAll().size());
    }
}
