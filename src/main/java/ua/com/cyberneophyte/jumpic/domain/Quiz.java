package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.*;
import java.util.List;

@Entity
@Table(name = "quiz")
@PrimaryKeyJoinColumn(name = "lesson_id")
public class Quiz extends Lesson {
    @Lob
    private String question;
    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER, orphanRemoval = true, cascade = CascadeType.PERSIST)
    private List<Answer> answers;
    private Integer points;

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
    }

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }

    public List<Answer> getAnswers() {
        return answers;
    }

    public void setAnswers(List<Answer> answers) {
        this.answers = answers;
    }

}
