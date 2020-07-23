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
import ua.com.cyberneophyte.jumpic.repos.*;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql", "/create-course.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql", "/delete-course.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class ModuleControllerTest {


    @Autowired
    MockMvc mockMvc;
    @Autowired
    ModuleRepo moduleRepo;
    private LinkedMultiValueMap<String, String> newModuleParams = new LinkedMultiValueMap<>();


    @BeforeEach
    public void initCourseFormWithCorrectValues() {
        newModuleParams.add("moduleName", "module1");
    }

    @AfterEach
    public void resetModuleForm() {
        newModuleParams.clear();
    }

    @Test
    @WithUserDetails("testUser")
    public void createModuleWithCorrectNameTest() throws Exception {
        this.mockMvc.perform(post("/courseEditor/1/addModule").params(newModuleParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[2]").string("module1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[1]").string("1"));
        assertTrue(moduleRepo.findAll().size() == 1);
    }

    @Test
    @WithUserDetails("testUser")
    @Disabled//until REST realization
    public void createModuleWithEmptyNameTest() throws Exception {
        newModuleParams.remove("moduleName");
        newModuleParams.add("moduleName", "");
        this.mockMvc.perform(post("/courseEditor/1/addModule").params(newModuleParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
               /* .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[2]").string("module1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[1]").string("1"))*/;
        assertTrue(moduleRepo.findAll().size() == 0);
    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-modules.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void editModuleNameTest() throws Exception {
        newModuleParams.remove("moduleName");
        newModuleParams.add("moduleName", "edited module 1");
        this.mockMvc.perform(post("/courseEditor/1/editModule/1").params(newModuleParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[2]").string("edited module 1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[1]").string("1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[4]/div[1]/div[2]").string("module 4"))
                .andExpect(xpath("/html/body/div[1]/div[4]/div[1]/div[1]").string("4"));
        assertTrue(moduleRepo.findAll().size() == 6);

    }

    @Test
    @WithUserDetails("testUser")
    @Sql(value = {"/create-user-before.sql", "/create-course.sql", "/create-modules.sql"},
            executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    @Sql(value = {"/create-user-after.sql", "/delete-course.sql"},
            executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
    public void deleteModuleTest() throws Exception {
        this.mockMvc.perform(post("/courseEditor/1/deleteModule/1").with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/courseEditor/1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[2]").string("module 2"))
                .andExpect(xpath("/html/body/div[1]/div[1]/div[1]/div[1]").string("1"));
        this.mockMvc.perform(get("/courseEditor/1"))
                .andExpect(xpath("/html/body/div[1]/div[4]/div[1]/div[2]").string("module 5"))
                .andExpect(xpath("/html/body/div[1]/div[4]/div[1]/div[1]").string("4"));
        assertTrue(moduleRepo.findAll().size() == 5);

    }
}
