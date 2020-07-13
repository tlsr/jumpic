package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.service.CourseService;
import ua.com.cyberneophyte.jumpic.service.ModuleService;

@Controller
@RequestMapping("/courseEditor")
public class CourseEditorController {
    private final CourseService courseService;
    private final ModuleService moduleService;

    public CourseEditorController(CourseService courseService,ModuleService moduleService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
    }

    @GetMapping("/{course}")
    @PreAuthorize("#course.courseInfo.author.id == authentication.principal.id")
    public String showCourseEditorTemplate(Model model, Module module, Course course,Chapter chapter){
        model.addAttribute("course",course);
        model.addAttribute("module",module);
        model.addAttribute("chapter",chapter);
        return "/courseEditor";
    }

    @PostMapping("/{course}/addModule")
    public String addModuleToCourse(Model model, Module module, Course course){
        moduleService.addModuleToCourse(module,course);
        model.addAttribute("course",course);
        model.addAttribute("module",module);
        return "redirect:/courseEditor/{course}";
    }

    @PostMapping("/{course}/editModule/{module}")
    public String editModuleInCourse(Model model, Module module, Course course){
        moduleService.editModuleInCourse(module,course);
        model.addAttribute("course",course);
        model.addAttribute("module",module);
        return "redirect:/courseEditor/{course}";
    }

    @PostMapping("/{course}/deleteModule/{module}")
    public String deleteModuleFromCourse(Model model, Module module, Course course){
        moduleService.deleteModuleFromCourse(module,course);
        model.addAttribute("course",course);
        model.addAttribute("module",module);
        return "redirect:/courseEditor/{course}";
    }


}
