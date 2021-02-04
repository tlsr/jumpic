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
//import ua.com.cyberneophyte.jumpic.service.Helper;
import ua.com.cyberneophyte.jumpic.service.ModuleService;

@Controller
@RequestMapping("/courseEditor")
public class ModuleController {
    private final ModuleService moduleService;
  //  private final Helper<Module> helper;

    public ModuleController(ModuleService moduleService) {
        this.moduleService = moduleService;
      // Helper<Module> helper  this.helper = helper;
    }

    @GetMapping("/{course}")
    @PreAuthorize("#course.courseInfo.author.id == authentication.principal.id")
    public String showCourseEditorTemplate(Model model, Module module, Course course, Chapter chapter) {
        return "courseEditor";
    }

    @PostMapping("/{course}/addModule")
    public String addModuleToCourse(Model model, Module module, Course course) {
        moduleService.addModuleToCourse(module, course);
        return "redirect:/courseEditor/{course}";
    }

    @PostMapping("/{course}/editModule/{module}")
    public String editModuleInCourse(Model model, Module module, Course course) {
        moduleService.editModuleInCourse(module, course);
        return "redirect:/courseEditor/{course}";
    }


    @PostMapping("/{course}/deleteModule/{module}")
    public String deleteModuleFromCourse(Model model, Module module, Course course) {
       // moduleService.deleteModuleFromCourse(module, course);
       // helper.deleteElementFromList(module,course.getListOfModules());
        return "redirect:/courseEditor/{course}";
    }

}
