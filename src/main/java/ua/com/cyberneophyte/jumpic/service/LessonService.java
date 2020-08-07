package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Lesson;
import ua.com.cyberneophyte.jumpic.repos.ChapterRepo;
import ua.com.cyberneophyte.jumpic.repos.LessonRepo;

import java.util.List;

@Service
@Transactional
public class LessonService {
    private final LessonRepo lessonRepo;
    private final ChapterRepo chapterRepo;


    public LessonService(LessonRepo lessonRepo, ChapterRepo chapterRepo) {
        this.lessonRepo = lessonRepo;
        this.chapterRepo = chapterRepo;
    }

    public void addLessonToChapter(Lesson lesson, Chapter chapter) {
        List<Lesson> listOfLessons = chapterRepo.findChapterById(chapter.getId()).getListOfLessons();
        StructuredUtil.incrementConsecutiveNumber(lesson, listOfLessons);
        lesson.setChapter(chapter);
        listOfLessons.add(lesson);
        lessonRepo.save(lesson);
    }


    public void deleteLessonFromChapter(Lesson lesson, Chapter chapter) {
        List<Lesson> listOfLessons = chapter.getListOfLessons();
        StructuredUtil.decrementConsecutiveNumber(lesson, listOfLessons);
        listOfLessons.remove(lesson);
        deleteLesson(lesson);
    }

    public void deleteLesson(Lesson lesson) {
        lessonRepo.deleteLessonById(lesson.getId());
    }

    public Lesson findLessonById(Long lessonId) {
        return lessonRepo.findLessonById(lessonId);
    }
}
