package com.servico.repository;

import com.servico.model.entity.Task;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.orm.jpa.DataJpaTest;
import org.springframework.test.context.ActiveProfiles;

import javax.annotation.security.RunAs;

import static org.junit.jupiter.api.Assertions.*;

@RunAs("dev")
@DataJpaTest
@ActiveProfiles("test")
class TaskRepositoryTest {

    @Autowired
    private TaskRepository repository;

    @Test
    void shouldLoadTask_WhenFindByDescription() {
        String description = "Task 1";
        Task task = repository.findByDescription(description);
        assertNotNull(task);
        assertEquals(description, task.getDescription());
    }

    @Test
    void shouldNotLoadTask_WhenDescriptionDoesntExists() {
        String description = "Task 3";
        Task task = repository.findByDescription(description);
        assertNull(task);
    }
}