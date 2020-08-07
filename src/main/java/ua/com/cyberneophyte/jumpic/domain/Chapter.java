package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.*;
import java.util.Comparator;
import java.util.List;
import java.util.stream.Collectors;

@Entity
@Table(name = "chapters")
public class Chapter implements Structured {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chapterName;
    @OneToMany(mappedBy = "chapter", orphanRemoval = true, cascade = CascadeType.PERSIST, fetch = FetchType.EAGER)
    private List<Lesson> listOfLessons;
    private Integer consecutiveNumber;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

    public List<Lesson> getOrderedListOfLessons() {
        List<Lesson> sortedList = listOfLessons.stream()
                .sorted(Comparator.comparingInt(Lesson::getConsecutiveNumber))
                .collect(Collectors.toList());
        return sortedList;
    }

    public List<Lesson> getListOfLessons() {
        return listOfLessons;
    }

    public void setListOfLessons(List<Lesson> lessons) {
        this.listOfLessons = lessons;
    }

    public Module getModule() {
        return module;
    }

    public void setModule(Module module) {
        this.module = module;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getChapterName() {
        return chapterName;
    }

    public void setChapterName(String chapterName) {
        this.chapterName = chapterName;
    }

    public Integer getConsecutiveNumber() {
        return consecutiveNumber;
    }

    public void setConsecutiveNumber(Integer order) {
        this.consecutiveNumber = order;
    }
}
