package ua.com.cyberneophyte.jumpic.forms;

import javax.validation.constraints.*;

public class CourseInfoForm {
    //TODO make better validation

    @Size(min = 6,max = 255)
    private String title;
    @NotEmpty
    @NotBlank
    private String tags;
    @Min(1)
    @Max(255)
    private int estimatedTimeToFinish;
    @NotEmpty
    @NotBlank
    private String description;

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public String getTitle() {
        return title;
    }

    public void setTitle(String title) {
        this.title = title;
    }

    public String getTags() {
        return tags;
    }

    public void setTags(String tags) {
        this.tags = tags;
    }

    public int getEstimatedTimeToFinish() {
        return estimatedTimeToFinish;
    }

    public void setEstimatedTimeToFinish(int estimatedTimeToFinish) {
        this.estimatedTimeToFinish = estimatedTimeToFinish;
    }
}
