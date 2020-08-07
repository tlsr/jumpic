package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Lesson;
import ua.com.cyberneophyte.jumpic.domain.Theory;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.service.LessonService;

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
                                          @PathVariable Long chapter, Lesson lesson) {
        Chapter chapter1 = chapterRepo.findChapterById(chapter);
        lessonService.deleteLessonFromChapter(lesson, chapter1);
        return "redirect:/courseEditor/{course}";
    }

    @GetMapping("/{lesson}/editLesson")
    public String editLessonInChapter(Model model, Chapter chapter, Lesson lesson) {
        if (lesson instanceof Theory) {
            return "redirect:/courseEditor/{course}/{module}/{chapter}/{lesson}/editTheory";
        } else {
            return "redirect:/courseEditor/{course}/{module}/{chapter}/{lesson}/editQuiz";
        }
    }

}
