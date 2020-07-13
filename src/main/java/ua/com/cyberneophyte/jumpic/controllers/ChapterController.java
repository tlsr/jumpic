package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.service.CourseService;
import ua.com.cyberneophyte.jumpic.service.ModuleService;

@Controller
@RequestMapping("/courseEditor/{course}/{module}")
public class ChapterController {
    private final CourseService courseService;
    private final ModuleService moduleService;

    public ChapterController(CourseService courseService, ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @PostMapping("/addChapter")
    public String addChapterToModule(Model model, Module module, Course course, Chapter chapter){
        model.addAttribute("chapter",chapter);
        System.out.println(module.getModuleName());
        System.out.println(course.getId());

        return "redirect:/courseEditor/{course}";
    }
}
