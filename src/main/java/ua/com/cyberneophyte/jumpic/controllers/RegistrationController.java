package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.cyberneophyte.jumpic.domain.Role;
import ua.com.cyberneophyte.jumpic.domain.User;
import ua.com.cyberneophyte.jumpic.forms.RegistrationForm;
import ua.com.cyberneophyte.jumpic.repos.UserRepo;
import javax.validation.Valid;
import java.util.Collections;

@Controller
public class RegistrationController {
    private final UserRepo userRepo;
    private final Validator registrationFormValidator;

    public RegistrationController(UserRepo userRepo, @Qualifier("registrationFormValidator") Validator registrationFormValidator) {
        this.userRepo = userRepo;
        this.registrationFormValidator = registrationFormValidator;
    }

    @GetMapping("/registration")
    public String registration(RegistrationForm registrationForm, Model model) {
        model.addAttribute("message", "hi from thymeleaf");
        model.addAttribute("registrationForm", registrationForm);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        registrationFormValidator.validate(registrationForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            User user = createUserFroRegistrationForm(registrationForm);
            userRepo.save(user);
        }
        return "redirect:/login";
    }

    private User createUserFroRegistrationForm(@Valid RegistrationForm registrationForm) {
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(registrationForm.getPassword());
        user.setActive(true);
        user.setEmail(registrationForm.getEmail());
        user.setAge(registrationForm.getAge());
        user.setRoles(Collections.singleton(Role.USER));
        return user;
    }

}
