package com.shpp.rstefanyshyn.spring.controllers;

import com.shpp.rstefanyshyn.spring.config.aop.LogExecutionTime;
import com.shpp.rstefanyshyn.spring.exeption.InvalidDataException;
import com.shpp.rstefanyshyn.spring.exeption.TaskNotFoundException;
import com.shpp.rstefanyshyn.spring.model.Task;
import com.shpp.rstefanyshyn.spring.repository.TaskRepository;
import com.shpp.rstefanyshyn.spring.services.TaskModelAssembler;
import com.shpp.rstefanyshyn.spring.services.TaskService;
import com.shpp.rstefanyshyn.spring.statemachine.Status;
import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.*;

import java.time.LocalDate;
import java.util.Locale;
import java.util.ResourceBundle;

@SecurityScheme(
        name = "Authorize",
        scheme = "Basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
@Slf4j
@RestController
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;
    private static final String PATH_MESSAGES = "messages";

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskModelAssembler assembler,
                          StateMachine<Status, StatusEvent> stateMachine, TaskService taskService, StateMachineFactory<Status, StatusEvent> stateMachineFactory) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;

    }

    @GetMapping("/task")
    @LogExecutionTime
    public CollectionModel<EntityModel<Task>> all() {
        return taskService.getAll();
    }

    @GetMapping("/")
    @LogExecutionTime
    public String startPage(Locale locale) {


        ResourceBundle bundle = ResourceBundle.getBundle(PATH_MESSAGES, locale);
        return bundle.getString("helloworld");
    }

    @GetMapping("/task/{id}")
    @LogExecutionTime
    public EntityModel<Task> one(@PathVariable Long id) {
        return taskService.getOne(id);
    }

    //  @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Authorize")

    @PostMapping("/task")
    @LogExecutionTime

    Task newTask(@RequestBody Task task, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        if (task.getTargetDate().isBefore(LocalDate.now())) {
            throw new InvalidDataException(task.getTargetDate() + " " + bundle.getString("invalidDataExceptionMessages"));
        }
        task.setState(String.valueOf(Status.PLANNED));
        return taskRepository.save(task);
    }

    @PutMapping("/task/{id}/status")
    @LogExecutionTime
    public ResponseEntity<Task> changeState1(@RequestBody Task task1, Locale locale) {
        long id = task1.getId();
        if (taskRepository.existsById(id)) {
            return taskService.changeState(task1, locale);
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            throw new TaskNotFoundException(id, bundle.getString("notFoundTaskMessages"));
        }
    }

    @SecurityRequirement(name = "Authorize")
    @DeleteMapping("/task/{id}")
    @LogExecutionTime
    void deleteTask(@PathVariable Long id, Locale locale) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);
            throw new TaskNotFoundException(id, " " + bundle.getString("notFoundTaskMessages"));
        }
    }
}














