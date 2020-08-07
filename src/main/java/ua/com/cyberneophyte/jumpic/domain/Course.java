package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(cascade = CascadeType.ALL)
    @JoinColumn(name = "courseinfo_id")
    private CourseInfo courseInfo;
    @OneToMany(mappedBy = "course", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Module> listOfModules;


    public List<Module> getListOfModules() {
        return listOfModules;
    }

    public void setListOfModules(List<Module> listOfModules) {
        this.listOfModules = listOfModules;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public CourseInfo getCourseInfo() {
        return courseInfo;
    }

    public void setCourseInfo(CourseInfo courseInfo) {
        this.courseInfo = courseInfo;
    }
}
