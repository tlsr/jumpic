package ua.com.cyberneophyte.jumpic;

import org.hamcrest.Matcher;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.security.test.context.support.WithUserDetails;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;

import java.util.regex.Pattern;

import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.xpath;
import static org.hamcrest.core.StringContains.containsString;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class UserControllerTest {

    @Autowired
    MockMvc mockMvc;

    @Test
    @WithUserDetails("testUser")
    public void accessDenied() throws Exception{
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isForbidden());
    }


    @Test
    @WithUserDetails("admin")
    public void userListTest() throws  Exception{
        mockMvc.perform(get("/user"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/table/tbody/tr").nodeCount(2))
                .andExpect(xpath("/html/body/table/tbody/tr[1]/td[1]").string("testUser"))
                .andExpect(xpath("/html/body/table/tbody/tr[2]/td[1]").string("admin"));
    }

    @Test
    @WithUserDetails("admin")
    public void userEditViewTest() throws  Exception{
        mockMvc.perform(get("/user/1"))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/form/table/tbody/tr[2]/td[1]").string("testUser"))
                .andExpect(xpath("/html/body/form/table/tbody/tr[4]/td[1]").string("[USER]"))
                .andExpect(xpath("/html/body/form/table/tbody/tr[4]/td[2]/div[2]").nodeCount(1));
    }

    @Test
    @WithUserDetails("admin")
    public void userEditUserRoleTest() throws  Exception{
        mockMvc.perform(post("/user/1").param("roles","USER").param("_roles","on")
                .param("roles","ADMIN").param("_roles","on").with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/form/table/tbody/tr[2]/td[1]").string("testUser"))
                .andExpect(xpath("/html/body/form/table/tbody/tr[4]/td[1]").string(containsString("USER")))
                .andExpect(xpath("/html/body/form/table/tbody/tr[4]/td[1]").string(containsString("ADMIN")))
                .andExpect(xpath("/html/body/form/table/tbody/tr[4]/td[2]/div[2]").nodeCount(1));
    }


}
