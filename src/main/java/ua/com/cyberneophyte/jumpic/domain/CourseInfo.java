package ua.com.cyberneophyte.jumpic.domain;

import ua.com.cyberneophyte.jumpic.forms.CourseInfoForm;

import javax.persistence.*;
import java.util.HashSet;
import java.util.Set;
import java.util.stream.Collectors;
import java.util.stream.Stream;

@Entity
@Table(name = "courseInfo")
public class CourseInfo {
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    @OneToOne(mappedBy = "courseInfo")
    private Course course;
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User author;
    private String title;
   /* @ManyToMany(cascade = CascadeType.ALL)
    @JoinTable(name = "course_tags",
            joinColumns = @JoinColumn(name = "courseInfo_id"),
            inverseJoinColumns = @JoinColumn(name = "tag_id")
    )*/
    @ElementCollection
    @CollectionTable(name = "course_tags", joinColumns = @JoinColumn(name = "courseInfo_id"))
    @Column(name = "tag")
    private Set<String> tags;
    private Integer estimatedTimeToFinish;
    private Integer points;

    public CourseInfo() {
    }

    public CourseInfo(CourseInfoForm courseInfoForm,User user,Course course){
        this.setEstimatedTimeToFinish(courseInfoForm.getEstimatedTimeToFinish());
        this.setTitle(courseInfoForm.getTitle());
        this.setAuthor(user);
        Set<String> tags = Stream.of(courseInfoForm.getTags().split(","))
                .map(line -> line.trim())
                .collect(Collectors.toSet());
        this.setTags(tags);
        this.setAuthor(user);
        this.setCourse(course);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Course getCourse() {
        return course;
    }

    public void setCourse(Course course) {
        this.course = course;
    }

    public User getAuthor() {
        return author;
    }

    public void setAuthor(User author) {
        this.author = author;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Set<String> getTags() {
        return tags;
    }

    public void setTags(Set<String> tags) {
        this.tags = tags;
    }

    public Integer getEstimatedTimeToFinish() {
        return estimatedTimeToFinish;
    }

    public void setEstimatedTimeToFinish(Integer estimatedTimeToFinish) {
        this.estimatedTimeToFinish = estimatedTimeToFinish;
    }

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }
}
