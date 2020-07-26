package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.validation.BindingResult;
import org.springframework.web.bind.annotation.*;
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

    //wy doesnt work with Chapter chapter
    @PostMapping("/{lesson}/deleteLesson")
    public String deleteLessonFromChapter(Model model,
                                          @PathVariable Long chapter, Lesson lesson){
        Chapter chapter1 = chapterRepo.findChapterById(chapter);
        lessonService.deleteLessonFromChapter(lesson,chapter1);
        return "redirect:/courseEditor/{course}";
    }

    @GetMapping("/{lesson}/editLesson")
    public String editLessonInChapter(Model model, Chapter chapter, Lesson lesson){
        System.out.println("we are here");
        if(lesson instanceof Theory){
            return "redirect:/courseEditor/{course}/{module}/{chapter}/{lesson}/edditTheory";
        }else {
            return "redirect:/courseEditor/{course}/{module}/{chapter}/{lesson}/edditQuiz";
        }
    }

}
