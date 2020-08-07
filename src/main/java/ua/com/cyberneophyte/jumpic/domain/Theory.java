package ua.com.cyberneophyte.jumpic.domain;

import javax.persistence.Entity;
import javax.persistence.Lob;
import javax.persistence.PrimaryKeyJoinColumn;
import javax.persistence.Table;

@Entity
@Table(name = "theory")
@PrimaryKeyJoinColumn(name = "lesson_id")
public class Theory extends Lesson {
    @Lob
    private String content;

    public Theory() {
    }

    public Theory(Long id, String title, String content) {
        super(id, title);
        this.content = content;
    }

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }

}
