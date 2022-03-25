package com.servico.model.dto;

import com.servico.model.entity.Task;

import java.time.LocalDate;

public class TaskDTO {

    private final Long id;
    private final String description;
    private final LocalDate creationDate;
    private final LocalDate finishDate;
    private final Integer priority;

    public TaskDTO(Task task) {
        this.id = task.getId();
        this.description = task.getDescription();
        this.creationDate = task.getCreationDate();
        this.finishDate = task.getFinishDate();
        this.priority = task.getPriority();
    }

    public Long getId() {
        return id;
    }

    public String getDescription() {
        return description;
    }

    public LocalDate getCreationDate() {
        return creationDate;
    }

    public LocalDate getFinishDate() {
        return finishDate;
    }

    public Integer getPriority() {
        return priority;
    }
}
