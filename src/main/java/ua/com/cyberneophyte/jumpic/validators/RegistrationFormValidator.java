package ua.com.cyberneophyte.jumpic.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.com.cyberneophyte.jumpic.forms.RegistrationForm;
import ua.com.cyberneophyte.jumpic.repos.UserRepo;

@Service
public class RegistrationFormValidator implements Validator {
    private final UserRepo userRepo;

    public RegistrationFormValidator(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return RegistrationForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        RegistrationForm registrationForm = (RegistrationForm) o;

        if (userRepo.findByEmail(registrationForm.getEmail()) != null) {
            errors.rejectValue("email", "email.exists");
        }
        if (userRepo.findByUsername(registrationForm.getUsername()) != null) {
            errors.rejectValue("username", "user.exists");
        }
        if (!registrationForm.getPassword().equals(registrationForm.getConfirmPassword())) {
            errors.rejectValue("password", "passwords.doesnt.match");
        }
    }
}
