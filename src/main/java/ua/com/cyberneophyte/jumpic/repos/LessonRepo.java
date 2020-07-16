package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.repository.NoRepositoryBean;
import ua.com.cyberneophyte.jumpic.domain.Lesson;

public interface LessonRepo extends JpaRepository<Lesson,Long> {
    void deleteLessonById(Long lessonId);
    Lesson findLessonById(Long lessonId);
}
