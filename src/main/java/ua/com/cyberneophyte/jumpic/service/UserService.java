package ua.com.cyberneophyte.jumpic.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Lazy;
import org.springframework.security.core.userdetails.UserDetails;
import org.springframework.security.core.userdetails.UserDetailsService;
import org.springframework.security.core.userdetails.UsernameNotFoundException;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import ua.com.cyberneophyte.jumpic.domain.Role;
import ua.com.cyberneophyte.jumpic.domain.User;
import ua.com.cyberneophyte.jumpic.forms.RegistrationForm;
import ua.com.cyberneophyte.jumpic.repos.UserRepo;

import javax.validation.Valid;
import java.util.Collections;

@Service
public class UserService implements UserDetailsService {
    private final UserRepo userRepo;
    @Lazy
    @Autowired
    private PasswordEncoder passwordEncoder;
//@Lazy PasswordEncoder passwordEncoder  this.passwordEncoder = passwordEncoder;
    public UserService(UserRepo userRepo) {

        this.userRepo = userRepo;
    }

    @Override
    public UserDetails loadUserByUsername(String username) throws UsernameNotFoundException {
        return userRepo.findByUsername(username);
    }

    public void createUserFromRegistrationForm(@Valid RegistrationForm registrationForm) {
        User user = new User();
        user.setUsername(registrationForm.getUsername());
        user.setPassword(passwordEncoder.encode(registrationForm.getPassword()));
        user.setActive(true);
        user.setEmail(registrationForm.getEmail());
        user.setAge(registrationForm.getAge());
        user.setRoles(Collections.singleton(Role.USER));
        userRepo.save(user);
    }
}