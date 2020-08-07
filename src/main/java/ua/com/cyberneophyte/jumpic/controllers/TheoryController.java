package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Lesson;
import ua.com.cyberneophyte.jumpic.domain.Theory;
import ua.com.cyberneophyte.jumpic.forms.TheoryForm;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.service.LessonService;
import ua.com.cyberneophyte.jumpic.service.TheoryService;

import javax.validation.Valid;

@Controller
@RequestMapping("/courseEditor/{course}/{module}/{chapter}")
public class TheoryController {
    private final LessonService lessonService;
    private final TheoryService theoryService;


    public TheoryController(LessonService lessonService,  TheoryService theoryService) {
        this.lessonService = lessonService;
        this.theoryService = theoryService;
    }

    @GetMapping(value = "/theoryEditor")
    public String showTheoryEditorForAdding(Model model, Chapter chapter, TheoryForm theoryForm) {
        model.addAttribute("theoryForm", theoryForm);
        model.addAttribute("actionLink", "addTheoryLesson");
        return "theoryEditor";
    }

    @PostMapping("/addTheoryLesson")
    public String addTheoryLessonToChapter(Model model,
                                           @PathVariable Chapter chapter,
                                           Theory theory,
                                           @Valid TheoryForm theoryForm,
                                           BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "theoryEditor";
        }
        theory = theoryService.createTheoryFromTheoryForm(theoryForm);
        lessonService.addLessonToChapter(theory, chapter);
        return "redirect:/courseEditor/{course}";
    }


    @PostMapping("/{lesson}/editTheoryLesson")
    public String editTheoryLessonInChapter(Model model,
                                            @PathVariable Chapter chapter,
                                            Theory theory,
                                            @Valid TheoryForm theoryForm,
                                            BindingResult bindingResult
    ) {
        if (bindingResult.hasErrors()) {
            return "theoryEditor";
        }
        theory = theoryService.createTheoryFromTheoryForm(theoryForm);
        theoryService.editTheoryLessonInChapter(theory, chapter);
        return "redirect:/courseEditor/{course}";
    }

    @GetMapping(value = "/{lesson}/editTheory")
    public String editTheoryInChapterShowForm(TheoryForm theoryForm, Model model, Chapter chapter, Lesson lesson) {
        theoryForm = theoryService.createTheoryFormFromLessonId(lesson.getId());
        model.addAttribute("theoryForm",theoryForm);
        model.addAttribute("actionLink", "editTheoryLesson");
        return "theoryEditor";
    }
}
