package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import ua.com.cyberneophyte.jumpic.domain.Role;
import ua.com.cyberneophyte.jumpic.domain.User;
import ua.com.cyberneophyte.jumpic.repos.UserRepo;

@Controller
@RequestMapping("/user")
@PreAuthorize("hasAuthority('ADMIN')")
public class UserController {
    private final UserRepo userRepo;

    public UserController(UserRepo userRepo) {
        this.userRepo = userRepo;
    }

    @GetMapping
    public String userList(Model model){
        model.addAttribute("users", userRepo.findAll());
        return "userList";
    }

    @GetMapping("{user}")
    public String editUser(Model model, User user, Role role){
        model.addAttribute("user",user);
        model.addAttribute("roles",Role.values());
        return "useredit";
    }

    @PostMapping("{user}")
    public String updateUser(User user,
                             Model model,Role role){
        model.addAttribute("user",user);
        model.addAttribute("roles",Role.values());
        userRepo.save(user);
        return "useredit";
    }
}
