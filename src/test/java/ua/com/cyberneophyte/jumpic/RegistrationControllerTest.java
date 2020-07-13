package ua.com.cyberneophyte.jumpic;




import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Disabled;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.AutoConfigureMockMvc;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.TestPropertySource;
import org.springframework.test.context.jdbc.Sql;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.util.LinkedMultiValueMap;
import ua.com.cyberneophyte.jumpic.domain.User;
import ua.com.cyberneophyte.jumpic.repos.UserRepo;

import java.util.List;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static org.springframework.security.test.web.servlet.request.SecurityMockMvcRequestPostProcessors.csrf;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.result.MockMvcResultHandlers.print;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@SpringBootTest
@AutoConfigureMockMvc
@TestPropertySource("/application-test.properties")
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
@Sql(value = {"/create-user-after.sql"}, executionPhase = Sql.ExecutionPhase.AFTER_TEST_METHOD)
public class RegistrationControllerTest {
    @Autowired
    private MockMvc mockMvc;

    private  LinkedMultiValueMap<String, String> newUserParams  = new LinkedMultiValueMap<>();

    @Autowired
    private UserRepo userRepo;

    @BeforeEach
    public  void initUserFormWithCorrectValues(){

        newUserParams.add("username","testUser6");
        newUserParams.add("password","l18U48Yi");
        newUserParams.add("confirmPassword","l18U48Yi");
        newUserParams.add("email","testuser@test.com");
        newUserParams.add("age","21");
    }

    @AfterEach
    public void dropUserFormValues(){
        newUserParams.clear();
    }

    @Test
    public void accessToRegistrationFormWithoutAuthorizationTest() throws Exception{
        this.mockMvc.perform(get("/registration"))
                .andDo(print())
                .andExpect(status().isOk());
    }

    @Test
    public void registrUserWhithCorrectFormTest() throws Exception{
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(redirectedUrl("/login"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.get(0).getUsername().equals("testUser6"));
    }

    @Test
    public void registrUserWithShortUserNameTest() throws Exception{
        newUserParams.remove("username");
        newUserParams.add("username","a");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p[1]")
                        .string("size must be between 4 and 50"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    public void registrUserWithLongUserNameTest() throws Exception{
        newUserParams.remove("username");
        newUserParams.add("username","addddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddddd");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p[1]")
                        .string("size must be between 4 and 50"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    public void registrUserWithForbidenSymbolsUserNameTest() throws Exception{
        newUserParams.remove("username");
        newUserParams.add("username","@@@##@#@");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p[1]")
                        .string("Only letters, numbers, \"_\",\"-\" are allowed"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    @Disabled
    public void registrUserWithShortPasswordTest() throws Exception{
        newUserParams.remove("password");
        newUserParams.remove("confirmPassword");
        newUserParams.add("confirmPassword","x");
        newUserParams.add("password","x");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[2]/div/p").exists());
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    @Disabled //for test
    public void registrUserWithPasswordThatDoesntMatchPatternTest() throws Exception{
        newUserParams.remove("password");
        newUserParams.remove("confirmPassword");
        newUserParams.add("confirmPassword","xxxxxxx");
        newUserParams.add("password","xxxxxxx");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[2]/div/p")
                        .exists());
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    public void registrUserWithPasswordThatDoesntMatchConfirmTest() throws Exception{
        newUserParams.remove("password");
        newUserParams.remove("confirmPassword");
        newUserParams.add("confirmPassword","l18U48Yi");
        newUserParams.add("password","l18U48Yy");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[2]/div/p")
                        .string("Passwords do not match"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    public void registrUserWithIncorrectEmailTest() throws Exception{
        newUserParams.remove("email");
        newUserParams.add("email","l18U48Yy");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[4]/div/p")
                        .string("Please enter correct email"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    public void registrUserWithLowAgeTest() throws Exception{
        newUserParams.remove("age");
        newUserParams.add("age","-1");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[5]/div/p")
                        .string("You should be at least 12 years old"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }


    @Test
    public void registrUserWithTooHighAgeTest() throws Exception{
        newUserParams.remove("age");
        newUserParams.add("age","666");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[5]/div/p")
                        .string("Please enter a correct age"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.isEmpty());
    }

    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void registrUserWithExistingUsernameTest() throws Exception{
        newUserParams.remove("username");
        newUserParams.add("username","testUser");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[1]/div/p[1]")
                        .string("User exists"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.size()==2);
    }
    @Test
    @Sql(value = {"/create-user-before.sql"}, executionPhase = Sql.ExecutionPhase.BEFORE_TEST_METHOD)
    public void registrUserWithExistingEmailTest() throws Exception{
        newUserParams.remove("email");
        newUserParams.add("email","test@test.com");
        this.mockMvc.perform(post("/registration").params(newUserParams).with(csrf()))
                .andDo(print())
                .andExpect(status().isOk())
                .andExpect(xpath("/html/body/div/form/div[4]/div/p")
                        .string("This email already in use"));
        List<User> usersList = userRepo.findAll();
        assertTrue(usersList.size()==2);
    }

}
