package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.cyberneophyte.jumpic.domain.*;
import ua.com.cyberneophyte.jumpic.forms.TheoryForm;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.service.LessonService;

import javax.validation.Valid;

@Controller
@RequestMapping("/courseEditor/{course}/{module}/{chapter}")
public class LessonsController {
    private final LessonService lessonService;
    private final ChapterRepo chapterRepo;

    public LessonsController(LessonService lessonService, ChapterRepo chapterRepo) {
        this.lessonService = lessonService;
        this.chapterRepo = chapterRepo;
    }

    @PostMapping("/deleteLesson/{lesson}")
    public String deleteLessonFromChapter(Model model, Chapter chapter, Lesson lesson){
        lessonService.deleteLessonFromChapter(lesson,chapter);
        return "redirect:/courseEditor/{course}";
    }

    @GetMapping("/editLesson/{lesson}")
    public String editLessonInChapter(Model model, Chapter chapter, Lesson lesson){
        System.out.println("we are here");
        if(lesson instanceof Theory){
            return "redirect:/courseEditor/{course}/{module}/{chapter}/edditTheory/{lesson}";
        }else {
            return "redirect:/courseEditor/{course}/{module}/{chapter}/edditQuiz/{lesson}";
        }
    }

}
