package com.servico.repository;

import com.servico.model.entity.Task;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import org.springframework.stereotype.Repository;

import java.util.List;

@Repository
public interface TaskRepository extends JpaRepository<Task, Long> {
    Task findByDescription(String description);

    @Query("SELECT t FROM Task t where t.priority > 1000")
    List<Task> findHighPriorityTasks();
}
