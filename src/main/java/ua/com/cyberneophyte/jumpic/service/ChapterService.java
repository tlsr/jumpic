package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.repos.ModuleRepo;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class ChapterService {
    private final ChapterRepo chapterRepo;
    private final ModuleRepo moduleRepo;

    public ChapterService(ChapterRepo chapterRepo, ModuleRepo moduleRepo) {
        this.chapterRepo = chapterRepo;
        this.moduleRepo = moduleRepo;
    }

    public void saveChapter(Chapter chapter){
        this.chapterRepo.save(chapter);
    }

    public void addChapterToModule(Chapter chapter, Module module){
        List<Chapter> listOfChapters = moduleRepo.findModuleById(module.getId()).getListOfChapters();
        StructuredService.incrementConsecutiveNumber(chapter,listOfChapters);
        listOfChapters.add(chapter);
        saveChapter(chapter);
    }

    public void deleteChapterFromModule(Chapter chapter,Module module) {
        List<Chapter> listOfChapters = module.getListOfChapters();
        StructuredService.decrementConsecutiveNumber(chapter,listOfChapters);
        listOfChapters.remove(chapter);
        deleteChapter(chapter);
    }



    public void editChapterInModule(Chapter chapter, Module module) {
        List<Chapter> listOfChapters = module.getListOfChapters();
        Iterator<Chapter> iterator = listOfChapters.iterator();
        while (iterator.hasNext()) {
            Chapter temp = iterator.next();
            if (temp.getId() == chapter.getId()) {
                temp.setChapterName(chapter.getChapterName());
            }
        }
        saveChapter(chapter);
    }

    public void deleteChapter(Chapter chapter) {
        chapterRepo.deleteChapterById(chapter.getId());
    }
}
