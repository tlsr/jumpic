package ua.com.cyberneophyte.jumpic.domain;
import javax.persistence.Entity;
import javax.persistence.GeneratedValue;
import javax.persistence.GenerationType;
import javax.persistence.Id;

@Entity
public class Quiz {
    @Id
    @GeneratedValue(strategy=GenerationType.AUTO)
    private Integer id;
    private String question;

    public String getQuestion() {
        return question;
    }

    public void setQuestion(String question) {
        this.question = question;
    }
}
