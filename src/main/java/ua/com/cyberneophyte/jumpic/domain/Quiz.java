package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;
import java.util.List;

//@Entity
public class Quiz implements Lesson {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Integer id;
    private String question;
    private Integer points;
    private Integer order;
    private List<Answer> listOfAnswers;
    private String title;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
