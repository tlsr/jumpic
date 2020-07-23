package ua.com.cyberneophyte.jumpic.validators;

import org.springframework.stereotype.Service;
import org.springframework.validation.Errors;
import org.springframework.validation.Validator;
import ua.com.cyberneophyte.jumpic.forms.CourseInfoForm;
import ua.com.cyberneophyte.jumpic.repos.CourseInfoRepo;


@Service
public class CourseInfoValidator implements Validator {
    private final CourseInfoRepo courseInfoRepo;

    public CourseInfoValidator(CourseInfoRepo courseInfoRepo) {
        this.courseInfoRepo = courseInfoRepo;
    }

    @Override
    public boolean supports(Class<?> aClass) {
        return CourseInfoForm.class.equals(aClass);
    }

    @Override
    public void validate(Object o, Errors errors) {
        CourseInfoForm courseInfoForm = (CourseInfoForm) o;
        if(courseInfoRepo.findCourseInfoByTitle(courseInfoForm.getTitle())!=null){
            errors.rejectValue("title","courseInfo.title.course.exists");
        }

    }
}
