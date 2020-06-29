package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.*;

@Entity
@Table(name = "modules")
public class Module {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String moduleName;
   // private List<Chapter> listOfChapters;
    private Integer points;
    private Integer consecutiveNumber;
    @ManyToOne
    @JoinColumn(name = "course_id")
    private Course course;

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public Module() {
    }

    public Module(String moduleName) {
        this.moduleName  =moduleName;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getModuleName() {
        return moduleName;
    }

    public void setModuleName(String moduleName) {
        this.moduleName = moduleName;
    }

/*    public List<Chapter> getListOfChapters() {
        return listOfChapters;
    }

    public void setListOfChapters(List<Chapter> listOfChapters) {
        this.listOfChapters = listOfChapters;
    }*/

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public Integer getConsecutiveNumber() {
        return consecutiveNumber;
    }

    public void setConsecutiveNumber(Integer order) {
        this.consecutiveNumber = order;
    }
}
