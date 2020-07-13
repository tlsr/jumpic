package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.*;

@Entity
@Table(name = "chapters")
public class Chapter implements Structured{
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    private String chapterName;
   // private java.util.List<Lesson> content;
    private Integer consecutiveNumber;
    @ManyToOne
    @JoinColumn(name = "module_id")
    private Module module;

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
