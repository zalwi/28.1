package pl.zalwi.data;

import org.springframework.format.annotation.DateTimeFormat;

import javax.persistence.*;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;

@Entity
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Enumerated(EnumType.STRING)
    private Category category;

    private String description;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime creationDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime deadlineDate;

    @DateTimeFormat(pattern = "yyyy-MM-dd HH:mm:ss")
    private LocalDateTime endDate;

    private Boolean finished;

    public Task() {
    }

    public Task(Long id, Category category, String description, LocalDateTime creationDate, LocalDateTime deadlineDate, LocalDateTime endDate, Boolean finished) {
        this.id = id;
        this.category = category;
        this.description = description;
        this.creationDate = creationDate;
        this.deadlineDate = deadlineDate;
        this.endDate = endDate;
        this.finished = finished;
    }

    public String getCreationDateInSqlDateTimeFormat() {
        if (creationDate != null) {
            return creationDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return null;
        }
    }

    public String getDeadlineDateInSqlDateTimeFormat() {
        if (deadlineDate != null) {
            return deadlineDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return null;
        }
    }

    public String getEndDateInSqlDateTimeFormat() {
        if (endDate != null) {
            return endDate.format(DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm"));
        } else {
            return null;
        }
    }

    public Status checkStatus() {
        if (!finished) {
            if (deadlineDate.isBefore(LocalDateTime.now())) {
                return Status.DELAYED;
            } else {
                return Status.INPROGRESS;
            }
        } else {
            if (deadlineDate.isBefore(endDate)) {
                return Status.DONEAFTER;
            } else {
                return Status.DONE;
            }
        }
    }

    public String calculateDelay() {
        if (deadlineDate.equals(null)) {
            return "00h00s";
        }
        return getDifferenceBetweenDateTimeInHoursAndMinutes(deadlineDate, LocalDateTime.now());
    }

    public String calculateLeftTime() {
        if (finished) {
            return "00h00s";
        }
        return getDifferenceBetweenDateTimeInHoursAndMinutes(LocalDateTime.now(), deadlineDate);
    }

    private String getDifferenceBetweenDateTimeInHoursAndMinutes(LocalDateTime firstDateTime, LocalDateTime secondDateTime) {
        long minutes = ChronoUnit.MINUTES.between(firstDateTime, secondDateTime);
        long hours = ChronoUnit.HOURS.between(firstDateTime, secondDateTime);
        long minutesWithoutHours = minutes % 60;
        return hours + "h" + minutesWithoutHours + "s";
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
        return "Task{" +
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
