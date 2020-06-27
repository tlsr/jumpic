package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.security.core.Authentication;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.domain.CourseInfo;
import ua.com.cyberneophyte.jumpic.domain.User;
import ua.com.cyberneophyte.jumpic.forms.CourseInfoForm;
import ua.com.cyberneophyte.jumpic.service.CourseService;

import javax.validation.Valid;

@Controller
public class CourseEditorController {
    private final CourseService courseService;

    public CourseEditorController(CourseService courseService) {
        this.courseService = courseService;
    }


    @GetMapping("/courseEditor")
    public String getCourseEditorPage(CourseInfoForm courseInfoForm){
        return "/courseEditor";
    }

    @PostMapping("/courseEditor")
    public String submitForm(Model model, @Valid CourseInfoForm courseInfoForm,
                             BindingResult bindingResult, Authentication authentication){
        User user =(User) authentication.getPrincipal();
        if (bindingResult.hasErrors()) {
            return "/courseEditor";
        } else {
            Course course = courseService.createCourseFromCourseInfoForm(courseInfoForm, user);
            courseService.saveCourse(course);
            return "redirect:/courseView/"+course.getCourseInfo().getId();
        }
    }

    @GetMapping("/courseView/{courseInfo}")
    public String displayCourse(Model model, CourseInfo courseInfo){
        model.addAttribute("courseInfo",courseInfo);
        return "/courseView";
    }
}
