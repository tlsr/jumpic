package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.*;
import java.util.List;
import java.util.Map;

@Entity
@Table(name = "quiz")
@PrimaryKeyJoinColumn(name = "lesson_id")
public class Quiz extends Lesson {
    @Lob
    private String question;
    @OneToMany(mappedBy = "quiz", fetch = FetchType.EAGER)
    private List<Answer> answers;

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

    @Override
    public String toString() {
        return "Quiz{" +
                "question='" + question + '\'' +
                ", answers=" + answers +
                '}';
    }
}
