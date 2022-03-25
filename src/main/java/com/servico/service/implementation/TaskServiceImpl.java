package com.servico.service.implementation;

import com.servico.exception.TaskNotFoundException;
import com.servico.model.dto.TaskDTO;
import com.servico.model.entity.Task;
import com.servico.model.form.TaskForm;
import com.servico.repository.TaskRepository;
import com.servico.service.TaskService;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.Cacheable;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.lang.NonNull;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class TaskServiceImpl implements TaskService {

    private final TaskRepository taskRepository;

    public TaskServiceImpl(TaskRepository taskRepository) {
        this.taskRepository = taskRepository;
    }

    @Override
    @Cacheable(value = "tasks")
    public List<TaskDTO> getTaskList() {
        List<Task> taskList = taskRepository.findAll();
        return taskList.stream().map(TaskDTO::new).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "pagedTasks")
    public List<TaskDTO> getTaskListByPage(Pageable pageable) {
        Page<Task> pageTask = taskRepository.findAll(pageable);
        return pageTask.toList().stream().map(TaskDTO::new).collect(Collectors.toList());
    }

    @Override
    @Cacheable(value = "tasks", key = "#id")
    public TaskDTO getTaskById(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        return task.map(TaskDTO::new)
                .orElseThrow(() -> new TaskNotFoundException("Tarefa não encontrada!"));
    }

    @Override
    public TaskDTO getTaskByDescription(String description) {
        Task task = taskRepository.findByDescription(description);
        if (task != null)
            return new TaskDTO(task);

        throw new TaskNotFoundException("Tarefa não encontrada!");
    }

    @Override
    @CacheEvict(value = {"tasks", "pagedTasks"}, allEntries = true)
    @Transactional
    public TaskDTO saveTask(TaskForm taskForm) {
        Task task = taskRepository.save(taskForm.convert());
        return new TaskDTO(task);
    }

    @Override
    @CacheEvict(value = {"tasks", "pagedTasks"}, allEntries = true)
    @Transactional
    public void deleteTask(Long id) {
        Optional<Task> task = taskRepository.findById(id);
        if (task.isPresent())
            taskRepository.delete(task.get());
        else
            throw new TaskNotFoundException("Tarefa não encontrada!");
    }

    @Override
    @CacheEvict(value = "tasks", allEntries = true)
    @Transactional
    public TaskDTO updateTask(Long id, @NonNull TaskForm taskForm) {
        Optional<Task> taskOptional = taskRepository.findById(id);
        if (taskOptional.isPresent()) {
            Task task = taskOptional.get();
            task.setDescription(taskForm.getDescription());
            task.setPriority(taskForm.getPriority());

            return new TaskDTO(task);
        }
        return null;
    }

    @Override
    public List<TaskDTO> findHighPriorityTasks() {
        List<Task> taskList = taskRepository.findHighPriorityTasks();
        return taskList.stream().map(TaskDTO::new).collect(Collectors.toList());
    }
}
