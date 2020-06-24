package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.validation.Validator;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.cyberneophyte.jumpic.forms.RegistrationForm;
import ua.com.cyberneophyte.jumpic.repos.UserRepo;
import ua.com.cyberneophyte.jumpic.service.UserService;

import javax.validation.Valid;

@Controller
public class RegistrationController {
    private final UserService userService;
    private final Validator registrationFormValidator;

    public RegistrationController(UserService userService ,UserRepo userRepo, @Qualifier("registrationFormValidator") Validator registrationFormValidator) {
        this.userService = userService;
        this.registrationFormValidator = registrationFormValidator;
    }

    @GetMapping("/registration")
    public String registration(RegistrationForm registrationForm, Model model) {
        model.addAttribute("registrationForm", registrationForm);
        return "registration";
    }

    @PostMapping("/registration")
    public String addUser(@Valid RegistrationForm registrationForm, BindingResult bindingResult, Model model) {
        registrationFormValidator.validate(registrationForm, bindingResult);
        if (bindingResult.hasErrors()) {
            return "registration";
        } else {
            userService.createUserFromRegistrationForm(registrationForm);
        }
        return "redirect:/login";
    }



}
