package ua.com.cyberneophyte.jumpic.forms;

import ua.com.cyberneophyte.jumpic.domain.Answer;

import java.util.ArrayList;
import java.util.List;

public class QuizForm {
    private Long id;
    private String title;
    private String question;
    private List<Answer> answers = new ArrayList<Answer>();

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
