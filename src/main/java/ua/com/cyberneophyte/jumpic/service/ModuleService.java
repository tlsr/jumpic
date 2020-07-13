package ua.com.cyberneophyte.jumpic.service;

import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import ua.com.cyberneophyte.jumpic.domain.Course;
import ua.com.cyberneophyte.jumpic.domain.Module;
import ua.com.cyberneophyte.jumpic.repos.CourseRepo;
import ua.com.cyberneophyte.jumpic.repos.ModuleRepo;

import java.util.Iterator;
import java.util.List;

@Service
@Transactional
public class ModuleService {
    private ModuleRepo moduleRepo;
    private CourseRepo courseRepo;

    public ModuleService(ModuleRepo moduleRepo, CourseRepo courseRepo) {
        this.moduleRepo = moduleRepo;
        this.courseRepo = courseRepo;
    }



    public void deleteModule(Module module) {
        moduleRepo.deleteModuleById(module.getId());
    }

    public void saveModule(Module module){
        moduleRepo.save(module);
    }

    public void addModuleToCourse(Module module, Course course) {
        List<Module> listOfModules = courseRepo.findCourseById(course.getId()).getListOfModules();
        StructuredService.incrementConsecutiveNumber(module,listOfModules);
        listOfModules.add(module);
        saveModule(module);
    }

    public void deleteModuleFromCourse(Module module, Course course) {
        List<Module> listOfModules = course.getListOfModules();
        StructuredService.decrementConsecutiveNumber(module,listOfModules);
        listOfModules.remove(module);
        deleteModule(module);
    }

    public void editModuleInCourse(Module module, Course course) {
        List<Module> listOfModules = course.getListOfModules();
        Iterator<Module> iterator = listOfModules.iterator();
        while (iterator.hasNext()) {
            Module temp = iterator.next();
            if (temp.getId() == module.getId()) {
                temp.setModuleName(module.getModuleName());
            }
        }
        saveModule(module);
    }
}
