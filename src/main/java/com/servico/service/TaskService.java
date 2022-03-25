package com.servico.service;

import com.servico.model.dto.TaskDTO;
import com.servico.model.entity.Task;
import com.servico.model.form.TaskForm;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;

import java.util.List;

public interface TaskService {
    List<TaskDTO> getTaskList();
    List<TaskDTO> getTaskListByPage(Pageable pageable);
    TaskDTO getTaskById(Long id);
    TaskDTO getTaskByDescription(String description);
    TaskDTO saveTask(TaskForm taskForm);
    void deleteTask(Long id);
    TaskDTO updateTask(Long id, @NonNull TaskForm taskForm);
    List<TaskDTO> findHighPriorityTasks();
}
