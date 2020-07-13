package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberneophyte.jumpic.domain.Chapter;
import ua.com.cyberneophyte.jumpic.domain.CourseInfo;

public interface ChapterRepo extends JpaRepository<Chapter,Long> {
    void deleteChapterById(Long id);
}
