package com.servico.model.form;

import com.servico.model.entity.Task;

import javax.validation.constraints.NotEmpty;
import javax.validation.constraints.NotNull;
import javax.validation.constraints.PositiveOrZero;

public class TaskForm {

    @NotNull
    @NotEmpty
    private String description;

    @NotNull
    @PositiveOrZero
    private Integer priority;

    public TaskForm(String description, Integer priority) {
        this.description = description;
        this.priority = priority;
    }

    public String getDescription() {
        return description;
    }

    public void setDescription(String description) {
        this.description = description;
    }

    public Integer getPriority() {
        return priority;
    }

    public void setPriority(Integer priority) {
        this.priority = priority;
    }

    public Task convert() {
        return new Task(description, priority);
    }
}
