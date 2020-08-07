package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import ua.com.cyberneophyte.jumpic.repos.CourseInfoRepo;

@Controller
public class MainPageController {
    private final CourseInfoRepo courseInfoRepo;

    public MainPageController(CourseInfoRepo courseInfoRepo) {
        this.courseInfoRepo = courseInfoRepo;
    }


    @GetMapping(value = {"/index", "/"})
    public String getHomePage(Model model) {
        model.addAttribute("coursesInfo", courseInfoRepo.findAll());
        return "index.html";
    }
}
