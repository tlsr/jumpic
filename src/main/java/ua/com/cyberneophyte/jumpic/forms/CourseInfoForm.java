package ua.com.cyberneophyte.jumpic.forms;

import javax.validation.constraints.Max;
import javax.validation.constraints.Min;
import javax.validation.constraints.NotBlank;
import javax.validation.constraints.Size;

public class CourseInfoForm {
    @Size(min = 3, max = 64, message = "{courseInfo.title.outside.range}")
    private String title;
    @NotBlank(message = "{courseInfo.tags.empty}")
    private String tags;
    @Min(value = 1, message = "{courseInfo.estimatedTime.to.small}")
    @Max(value = 255, message = "{courseInfo.estimatedTime.to.big}")
    private int estimatedTimeToFinish;
    @Size(min = 10, max = 500, message = "{courseInfo.descrition.outside.range}")
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
