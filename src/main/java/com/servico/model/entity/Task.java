package com.servico.model.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import org.springframework.lang.NonNull;

import javax.persistence.*;
import java.time.LocalDate;
import java.util.Objects;

@Entity
@Table(name = "task")
public class Task {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @NonNull
    private String description;
    private LocalDate creationDate;
    private LocalDate finishDate;
    private Integer priority;

    @JsonIgnore
    @ManyToOne(fetch = FetchType.LAZY)
    private User user;

    public Task() {
        description = "";
    }

    public Task(@NonNull String description, Integer priority) {
        this.description = description;
        this.creationDate = LocalDate.now();
        this.priority = priority;
    }

    public Long getId() {
        return id;
    }

    public void setId(Long id) {
        this.id = id;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public void setCreationDate(LocalDate creationDate) {
        this.creationDate = creationDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public void setFinishDate(LocalDate finishDate) {
        this.finishDate = finishDate;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    @Override
    public boolean equals(Object o) {
        if (this == o) return true;
        if (o == null || getClass() != o.getClass()) return false;

        Task task = (Task) o;

        if (!Objects.equals(id, task.id)) return false;
        if (!description.equals(task.description)) return false;
        if (!Objects.equals(creationDate, task.creationDate)) return false;
        if (!Objects.equals(finishDate, task.finishDate)) return false;
        return Objects.equals(priority, task.priority);
    }

    @Override
    public int hashCode() {
        int result = id != null ? id.hashCode() : 0;
        result = 31 * result + description.hashCode();
        result = 31 * result + (creationDate != null ? creationDate.hashCode() : 0);
        result = 31 * result + (finishDate != null ? finishDate.hashCode() : 0);
        result = 31 * result + (priority != null ? priority.hashCode() : 0);
        return result;
    }

    @Override
    public String toString() {
        return "Task{" +
                "id=" + id +
                ", description='" + description + '\'' +
                ", creationDate=" + creationDate +
                ", finishDate=" + finishDate +
                ", priority=" + priority +
                '}';
    }
}
