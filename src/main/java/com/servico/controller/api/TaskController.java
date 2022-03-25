package com.servico.controller.api;

import com.servico.exception.TaskNotFoundException;
import com.servico.model.dto.TaskDTO;
import com.servico.model.form.TaskForm;
import com.servico.service.TaskService;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.Sort;
import org.springframework.data.web.PageableDefault;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.util.UriComponentsBuilder;

import javax.validation.Valid;
import java.net.URI;
import java.util.ArrayList;
import java.util.List;

@RestController
@RequestMapping("/api/task")
public class TaskController {

    private final TaskService taskService;

    public TaskController(TaskService taskService) {
        this.taskService = taskService;
    }

    @GetMapping
    public ResponseEntity<List<TaskDTO>> listTasks() {
        List<TaskDTO> taskList = taskService.getTaskList();
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @GetMapping("/paged")
    public ResponseEntity<List<TaskDTO>> listTasks(@PageableDefault(sort = "id", direction = Sort.Direction.ASC) Pageable page) {
        List<TaskDTO> taskList = taskService.getTaskListByPage(page);
        return new ResponseEntity<>(taskList, HttpStatus.OK);
    }

    @GetMapping("/{id}")
    public ResponseEntity<TaskDTO> listTasksById(@PathVariable String id) {
        TaskDTO task = taskService.getTaskById(Long.valueOf(id));
        return new ResponseEntity<>(task, HttpStatus.OK);
    }

    @PostMapping("/newTask")
    public ResponseEntity<TaskDTO> createTask(@RequestBody @Valid TaskForm task, UriComponentsBuilder uriBuilder) {
        TaskDTO newTask = taskService.saveTask(task);
        if (newTask != null) {
            URI uri = uriBuilder.path("/api/task/{id}").buildAndExpand(newTask.getId()).toUri();
            return ResponseEntity.created(uri).body(newTask);
        } else
            throw new TaskNotFoundException("Tarefa não criada!");
    }

    @PostMapping("/newTaskList")
    public ResponseEntity<List<TaskDTO>> createTaskList(@RequestBody List<TaskForm> taskList) {
        List<TaskDTO> newTaskList = new ArrayList<>();
        taskList.forEach((task) -> newTaskList.add(taskService.saveTask(task)));
        return new ResponseEntity<>(newTaskList, HttpStatus.OK);
    }

    @PutMapping("/{id}")
    public ResponseEntity<TaskDTO> updateTask(@PathVariable Long id, @RequestBody @Valid TaskForm taskForm) {
        TaskDTO task = taskService.updateTask(id, taskForm);
        return ResponseEntity.ok(task);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Object> deleteTask(@PathVariable Long id) {
        taskService.deleteTask(id);
        return ResponseEntity.ok().build();
    }
}
