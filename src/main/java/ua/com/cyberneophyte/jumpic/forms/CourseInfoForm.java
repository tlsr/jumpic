package ua.com.cyberneophyte.jumpic.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;

public class CourseInfoForm {
    @NotNull
    @Size(min = 6,max = 255)
    private String title;
    @NotNull
    private String tags;
    @NotNull
    @Min(1)
    @Max(255)
    private int estimatedTimeToFinish;
    @NotNull
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
