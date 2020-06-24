package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.domain.CourseInfo;
import ua.com.cyberneophyte.jumpic.domain.User;
import ua.com.cyberneophyte.jumpic.forms.CourseInfoForm;
import ua.com.cyberneophyte.jumpic.repos.CourseInfoRepo;
import ua.com.cyberneophyte.jumpic.repos.CourseRepo;

@Service
public class CourseService {
    private final CourseRepo courseRepo;
    private final CourseInfoRepo courseInfoRepo;

    public CourseService(CourseRepo courseRepo, CourseInfoRepo courseInfoRepo) {
        this.courseRepo = courseRepo;
        this.courseInfoRepo = courseInfoRepo;
    }

    public Course findCourseById(Long courseId) {
        return courseRepo.findCourseById(courseId);
    }

    public Course findCourseByCourseInfo(CourseInfo courseInfo) {
        return courseRepo.findCourseByCourseInfo(courseInfo);
    }

    public Course findCourseByTitle(String title) {
        return courseInfoRepo.findCourseInfoByTitle(title).getCourse();
    }

    public void saveCourse(Course course) {
        courseRepo.save(course);
    }

    public Course createCourseFromCourseInfoForm(CourseInfoForm courseInfoForm, User user) {
        Course course = new Course();
        CourseInfo courseInfo = new CourseInfo(courseInfoForm, user, course);
        course.setCourseInfo(courseInfo);
        return course;
    }

}
