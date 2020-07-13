package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.service.ChapterService;
import ua.com.cyberneophyte.jumpic.service.CourseService;
import ua.com.cyberneophyte.jumpic.service.ModuleService;

@Controller
@RequestMapping("/courseEditor/{course}/{module}")
public class ChapterController {
    private final CourseService courseService;
    private final ModuleService moduleService;
    private final ChapterService chapterService;

    public ChapterController(CourseService courseService, ModuleService moduleService, ChapterService chapterService) {
        this.courseService = courseService;
        this.moduleService = moduleService;
        this.chapterService = chapterService;
    }

    @PostMapping("/addChapter")
    public String addChapterToModule(Model model, Module module, Course course, Chapter chapter){
        model.addAttribute("chapter",chapter);
        chapterService.addChapterToModule(chapter,module);
        return "redirect:/courseEditor/{course}";
    }

    @PostMapping("/deleteChapter/{chapter}")
    public String deleteChapterFromModule(Model model, Module module, Course course, Chapter chapter){
        chapterService.deleteChapterFromModule(chapter,module);
        return "redirect:/courseEditor/{course}";
    }
    @PostMapping("/editChapter/{chapter}")
    public String editChapterInModule(Model model, Module module, Course course,Chapter chapter){
        chapterService.editChapterInModule(chapter,module);
        return "redirect:/courseEditor/{course}";
    }
}
