package com.shpp.rstefanyshyn.spring.controllers;

import com.shpp.rstefanyshyn.spring.exeption.TaskNotFoundException;
import com.shpp.rstefanyshyn.spring.model.Task;
import com.shpp.rstefanyshyn.spring.repository.TaskRepository;
import com.shpp.rstefanyshyn.spring.services.TaskModelAssembler;
import com.shpp.rstefanyshyn.spring.services.TaskService;
import com.shpp.rstefanyshyn.spring.services.UpdateEventRequest;
import com.shpp.rstefanyshyn.spring.statemachine.Status;
import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
import io.swagger.annotations.ApiParam;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.enums.ParameterIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeIn;
import io.swagger.v3.oas.annotations.enums.SecuritySchemeType;
import io.swagger.v3.oas.annotations.media.Schema;
import io.swagger.v3.oas.annotations.security.SecurityRequirement;
import io.swagger.v3.oas.annotations.security.SecurityScheme;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.ResponseEntity;
import org.springframework.security.access.prepost.PreAuthorize;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.web.bind.annotation.*;
@SecurityScheme(
        name = "Java in use api",
        scheme = "Basic",
        type = SecuritySchemeType.HTTP,
        in = SecuritySchemeIn.HEADER)
@Slf4j
@RestController
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskService taskService;


    @Autowired
    public TaskController(TaskRepository taskRepository, TaskModelAssembler assembler,
                          StateMachine<Status, StatusEvent> stateMachine, TaskService taskService, StateMachineFactory<Status, StatusEvent> stateMachineFactory) {
        this.taskRepository = taskRepository;
        this.taskService = taskService;

    }

    @GetMapping("/task")
    public CollectionModel<EntityModel<Task>> all() {
        return taskService.getAll();
    }
    @GetMapping("/")
    public String startPage() {
        return "Hi, welcome to Task manager ";
    }
    @GetMapping("/task/{id}")
    public EntityModel<Task> one(@PathVariable Long id) {
        return taskService.getOne(id);
    }
  //  @PreAuthorize("hasRole('ADMIN')")
  @SecurityRequirement(name = "Java in use api")

    @PostMapping("/task")
    Task newTask(@RequestBody Task task) {
        task.setState(String.valueOf(Status.PLANNED));
        return taskRepository.save(task);
    }

    @PutMapping("/task/{id}/status")
    public ResponseEntity<Task> changeState1(@ApiParam(allowableValues = "one, two, three")
    @RequestBody Task task1) {

        return taskService.changeState(task1);
    }
//@Operation(
//        tags = "APIs",
//        summary = "Find XYZ",
//        parameters = {
//                @Parameter(
//                        in = ParameterIn.QUERY,
//                        name = "id",
//                        schema = @Schema(allowableValues = { "value1", "value2",
//                                "value3", "value4" }),
//                        description ="description ")
//        })
//
//@PutMapping("/task/{id}")
//    public ResponseEntity<Task> updateTaskStatus(@PathVariable Long id, @RequestBody UpdateEventRequest request) {
//        Task task = taskRepository.findById(id) //
//                .orElseThrow(() -> new TaskNotFoundException(id));
//        task.setEvent(String.valueOf(request.getEvent()));
//        taskRepository.save(task);
//        log.error("-----------------------------------{}",request.getEvent());
//        log.error("-----------------------------------{}",task.toString());
//       // Task updatedTask = taskService.changeState(task).getBody();
//        return taskService.changeState(task);
//        if (updatedTask != null) {
//            return ResponseEntity.ok(updatedTask);
//        } else {
//            return ResponseEntity.notFound().build();
//        }

    //   }
    // @PreAuthorize("hasRole('ADMIN')")
    @SecurityRequirement(name = "Java in use api")
    @DeleteMapping("/task/{id}")
    void deleteTask(@PathVariable Long id) {
        if (taskRepository.existsById(id)) {
            taskRepository.deleteById(id);
        } else {
            throw new TaskNotFoundException(id);
        }
    }
}














