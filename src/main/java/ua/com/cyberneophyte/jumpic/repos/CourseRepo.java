package ua.com.cyberneophyte.jumpic.repos;

import org.springframework.data.jpa.repository.JpaRepository;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.domain.CourseInfo;

import java.util.List;

public interface CourseRepo extends JpaRepository<Course,Long>{
    public Course findCourseById(Long id);
    public Course findCourseByCourseInfo(CourseInfo courseInfo);
}
