package ua.com.cyberneophyte.jumpic.controllers;

import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.service.ChapterService;

@Controller
@RequestMapping("/courseEditor/{course}/{module}")
public class ChapterController {
    private final ChapterService chapterService;

    public ChapterController(ChapterService chapterService) {
        this.chapterService = chapterService;
    }

    @PostMapping("/addChapter")
    public String addChapterToModule(Model model, Module module, Chapter chapter) {
        chapterService.addChapterToModule(chapter, module);
        return "redirect:/courseEditor/{course}";
    }

    @PostMapping("/deleteChapter/{chapter}")
    public String deleteChapterFromModule(Module module, Chapter chapter) {
        chapterService.deleteChapterFromModule(chapter, module);
        return "redirect:/courseEditor/{course}";
    }

    @PostMapping("/editChapter/{chapter}")
    public String editChapterInModule(Module module, Chapter chapter) {
        chapterService.editChapterInModule(chapter, module);
        return "redirect:/courseEditor/{course}";
    }
}
