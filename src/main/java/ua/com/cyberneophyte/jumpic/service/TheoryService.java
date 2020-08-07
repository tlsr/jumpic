package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.Lesson;
import ua.com.cyberneophyte.jumpic.domain.Theory;
import ua.com.cyberneophyte.jumpic.forms.TheoryForm;
import ua.com.cyberneophyte.jumpic.repos.TheoryRepo;

import java.util.List;

@Service
@Transactional
public class TheoryService {
    private final TheoryRepo theoryRepo;

    public TheoryService(TheoryRepo theoryRepo) {
        this.theoryRepo = theoryRepo;
    }

    public Theory createTheoryFromTheoryForm(TheoryForm theoryForm) {
        Theory theory = new Theory(theoryForm.getId(), theoryForm.getTitle(), theoryForm.getContent());
        return theory;
    }

    public void editTheoryLessonInChapter(Theory theory, Chapter chapter) {
        List<Lesson> listOfChapters = chapter.getListOfLessons();
        Theory oldTheory = (Theory) listOfChapters.stream()
                .filter(lesson ->
                        lesson.getId().equals(theory.getId())
                )
                .findFirst()
                .orElse(null);
        oldTheory.setContent(theory.getContent());
        oldTheory.setTitle(theory.getTitle());
        saveTheory(oldTheory);
    }

    public TheoryForm createTheoryFormFromLessonId(Long lessonId) {
        Theory theory = theoryRepo.findTheoryById(lessonId);
        TheoryForm theoryForm = new TheoryForm();
        theoryForm.setContent(theory.getContent());
        theoryForm.setTitle(theory.getTitle());
        theoryForm.setId(theory.getId());
        return theoryForm;
    }

    public void saveTheory(Theory theory) {
        theoryRepo.save(theory);
    }

}
