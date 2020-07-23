package ua.com.cyberneophyte.jumpic.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class TheoryForm {
    private Long id;
    @Size(min = 3,max = 64,message = "{theory.name.out.of.range}")
    private String title;
    @Size(min = 3,max = 2500,message = "{theory.content.out.of.range}")
    private String content;

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

    public String getContent() {
        return content;
    }

    public void setContent(String content) {
        this.content = content;
    }
}
