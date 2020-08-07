package ua.com.cyberneophyte.jumpic.forms;

import ua.com.cyberneophyte.jumpic.domain.Role;

import javax.validation.constraints.*;

public class RegistrationForm {
    @NotNull
    @Size(min = 4, max = 50)
    @Pattern(regexp = "^[a-zA-Z0-9_-]+",
            message = "{username.doesnt.match.pattern}")
    private String username;
    @NotNull
    /* Password matching expression. Password must be at least 8 characters,
    no more than 16 characters, and must include at least one upper case letter,
    one lower case letter, and one numeric digit.*/
    @Pattern(regexp = "^(?=.*\\d)(?=.*[a-z])(?=.*[A-Z]).{8,16}$",
            message = "{password.doesnt.match.pattern}")
    private String password;
    private String confirmPassword;
    @Max(value = 110, message = "{too.old}")
    @Min(value = 12, message = "{too.young}")
    private int age;
    @NotNull
    @Email(message = "{email.incorrect}")
    private String email;
    private Role roleRequest;

    public String getEmail() {
        return email;
    }

    public void setEmail(String email) {
        this.email = email;
    }

    public String getUsername() {
        return username;
    }

    public void setUsername(String username) {
        this.username = username;
    }

    public String getPassword() {
        return password;
    }

    public void setPassword(String password) {
        this.password = password;
    }

    public String getConfirmPassword() {
        return confirmPassword;
    }

    public void setConfirmPassword(String confirmPassword) {
        this.confirmPassword = confirmPassword;
    }

    public int getAge() {
        return age;
    }

    public void setAge(int age) {
        this.age = age;
    }

    public Role getRoleRequest() {
        return roleRequest;
    }

    public void setRoleRequest(Role roleRequest) {
        this.roleRequest = roleRequest;
    }
}
