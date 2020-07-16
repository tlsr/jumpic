package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.*;

@Entity
@Inheritance(strategy = InheritanceType.JOINED)
public class Lesson  implements  Structured{
    @Id
    @GeneratedValue(strategy = GenerationType.AUTO)
    private Long id;
    private String title;
    private Integer consecutiveNumber;
    @ManyToOne
    @JoinColumn(name = "chapter_id")
    private Chapter chapter;

    public Lesson() {
    }

    public Lesson(Long id,String title) {
        this.id = id;
        this.title = title;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public Integer getConsecutiveNumber() {
        return consecutiveNumber;
    }

    public void setConsecutiveNumber(Integer consecutiveNumber) {
        this.consecutiveNumber = consecutiveNumber;
    }

    public Chapter getChapter() {
        return chapter;
    }

    public void setChapter(Chapter chapter) {
        this.chapter = chapter;
    }

    @Override
    public String toString() {
        return "Lesson{" +
                "id=" + id +
                ", title='" + title + '\'' +
                ", consecutiveNumber=" + consecutiveNumber +
                ", chapter=" + chapter +
                '}';
    }
}
