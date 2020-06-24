package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberneophyte.jumpic.domain.CourseInfo;

public interface CourseInfoRepo extends JpaRepository<CourseInfo,Long> {
    CourseInfo findCourseInfoByTitle(String title);
}
