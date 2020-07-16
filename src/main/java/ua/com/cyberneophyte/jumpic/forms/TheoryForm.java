package ua.com.cyberneophyte.jumpic.forms;

import javax.validation.constraints.NotBlank;
import javax.validation.constraints.NotNull;

public class TheoryForm {
    private Long id;
    @NotBlank
    private String title;
    @NotBlank
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
