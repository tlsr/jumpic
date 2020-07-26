package ua.com.cyberneophyte.jumpic.forms;

import ua.com.cyberneophyte.jumpic.domain.Answer;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;
import java.util.ArrayList;
import java.util.List;

public class QuizForm {
    private Long id;
    @Size(min = 3,max = 64,message = "{lesson.title.out.of.range}")
    private String title;
    @Size(min = 3,max = 2500,message = "{quiz.question.out.of.range}")
    private String question;
    @Min(1)
    @Max(5)
    private Integer points;
    private List<Answer> answers = new ArrayList<Answer>();

    public Integer getPoints() {
        return points;
    }

    public void setPoints(Integer points) {
        this.points = points;
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
