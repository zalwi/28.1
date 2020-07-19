package pl.zalwi.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.EnumType;
import javax.persistence.Enumerated;
import java.time.LocalDateTime;

public class TaskForm {

    private Long id;
    private Category category;
    private String description;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime creationDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime deadlineDate;

    @DateTimeFormat(iso = DateTimeFormat.ISO.DATE_TIME)
    private LocalDateTime endDate;

    private Boolean finished;

    public TaskForm() {
    }

    public Task convertToTask(){
        Boolean isFinished;
//        if(finished != null){
//            isFinished=true;
//        }else{
//            isFinished=false;
//        }
        if(endDate != null){
            isFinished = true;
        }else{
            isFinished = false;
        }


        return new Task(id,category,description,creationDate,deadlineDate,endDate,isFinished);
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public Category getCategory() {
        return category;
    }

    public void setCategory(Category category) {
        this.category = category;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDateTime getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDateTime creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDateTime getDeadlineDate() {
        return deadlineDate;
    }

    public void setDeadlineDate(LocalDateTime deadlineDate) {
        this.deadlineDate = deadlineDate;
    }

    public LocalDateTime getEndDate() {
        return endDate;
    }

    public void setEndDate(LocalDateTime endDate) {
        this.endDate = endDate;
    }

    public Boolean getFinished() {
        return finished;
    }

    public void setFinished(Boolean finished) {
        this.finished = finished;
    }

    @Override
    public String toString() {
        return "TaskForm{" +
                "id=" + id +
                ", category=" + category +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", deadlineDate=" + deadlineDate +
                ", endDate=" + endDate +
                ", finished=" + finished +
                '}';
    }


}
