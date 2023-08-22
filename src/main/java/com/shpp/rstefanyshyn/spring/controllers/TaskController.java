package com.shpp.rstefanyshyn.spring.controllers;

import com.shpp.rstefanyshyn.spring.exeption.InvalidStatusException;
import com.shpp.rstefanyshyn.spring.exeption.TaskNotFoundException;
import com.shpp.rstefanyshyn.spring.model.Task;
import com.shpp.rstefanyshyn.spring.repository.TaskRepository;
import com.shpp.rstefanyshyn.spring.services.TaskModelAssembler;
import com.shpp.rstefanyshyn.spring.statemachine.Status;
import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@RestController
public class TaskController {
    private final TaskRepository taskRepository;
    private final TaskModelAssembler assembler;
    private StateMachine<Status, StatusEvent> stateMachine1;

    private final StateMachineFactory<Status, StatusEvent> stateMachineFactory;

    @Autowired
    public TaskController(TaskRepository taskRepository, TaskModelAssembler assembler,
                          StateMachine<Status, StatusEvent> stateMachine, StateMachineFactory<Status, StatusEvent> stateMachineFactory) {
        this.taskRepository = taskRepository;
        this.assembler = assembler;
        this.stateMachine1 = stateMachine;
        this.stateMachineFactory = stateMachineFactory;

    }

    @GetMapping("/task")
    public CollectionModel<EntityModel<Task>> all() {

        List<EntityModel<Task>> task = taskRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(task, linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }

    @GetMapping("/tasks/{id}")
    public EntityModel<Task> one(@PathVariable Long id) {

        Task task = taskRepository.findById(id) //
                .orElseThrow(() -> new TaskNotFoundException(id));

        return assembler.toModel(task);
    }


//    @PutMapping("/tasks/{id}")
//    public ResponseEntity<Task> replaceTask(@RequestBody Task newTask, @PathVariable Long id) {
//        return taskRepository.findById(id)
//                .map(task -> {
//                    task.setTaskDescription(newTask.getTaskDescription());
//                    task.setTargetDate(newTask.getTargetDate());
//                    task.setStatus(newTask.getStatus());
//                    Task updatedTask = taskRepository.save(task);
//
//                    // Відправте подію до машини стану
//                    stateMachine.sendEvent(StatusEvent.START);  // Або будь-яку іншу початкову подію
//
//                    return ResponseEntity.ok(updatedTask);
//                })
//                .orElseGet(() -> {
//                    newTask.setId(id);
//                    Task createdTask = taskRepository.save(newTask);
//
//                    // Відправте подію до машини стану
//                    stateMachine.sendEvent(StatusEvent.START);  // Або будь-яку іншу початкову подію
//
//                    return ResponseEntity.status(HttpStatus.CREATED).body(createdTask);
//                });
//    }

    @PutMapping("/tasks/{id}/status")
    public ResponseEntity<Task> changeState(@RequestBody Task task1) {
        StateMachine<Status, StatusEvent> sm = build(task1);
        sm.getExtendedState().getVariables().put("extendedState", task1.getExtendedState());
        stateMachine1= build(task1);
        sm.sendEvent(
                MessageBuilder.withPayload(StatusEvent.valueOf(task1.getEvent()))
                        .setHeader("taskId", task1.getId())

                        .setHeader("state", task1.getState())
                        .build()
        );
//        log.info("sm_____________ {}",  sm.getState().getIds());
//        log.info("sm_new_________ {}",  stateMachine1.getState().getIds());
                if (sm.getState().getId().equals(stateMachine1.getState().getId())) {
        log.error("State is already the same.");
                    throw new InvalidStatusException("Status is bad"+sm.getState().getId().toString());
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(task1);
    }

//    @PatchMapping("/tasks/{id}/status")
//    extendedState
//    public ResponseEntity<EntityModel<Task>> updateTaskStatus(@PathVariable Long id, @RequestParam StatusEvent newStatusEvent) {
//        return taskRepository.findById(id)
//                .map(task -> {
//                    StateMachine<Status, StatusEvent> stateMachine = stateMachineFactory.getStateMachine();
//
//                    // Отримуємо поточний контекст стану задачі
//                    StateMachineContext<Status, StatusEvent> currentState = task.getStateMachineContext();
//
//                    // Переініціалізуємо станову машину з поточним контекстом
//                    stateMachine.getStateMachineAccessor().doWithAllRegions(access -> {
//                        access.resetStateMachine(new DefaultStateMachineContext<>(currentState.getState(), null, null, null));
//                    });
//
//                    // Відправляємо подію до машини стану
//                    boolean transitionResult = stateMachine.sendEvent(newStatusEvent);
//
//                    if (transitionResult) {
//                        // Оновлюємо стан задачі
//                        task.setState(String.valueOf(stateMachine.getState().getId()));
//                        // Оновлюємо контекст стану
//                        task.setStateMachineContext(stateMachine.getExtendedState().get("stateMachineContext", StateMachineContext.class));
//                        Task updatedTask = taskRepository.save(task);
//                        return ResponseEntity.ok(assembler.toModel(updatedTask));
//                    } else {
//                        return ResponseEntity.badRequest().body(assembler.toModel(task));
//                    }
//                })
//                .orElseGet(() -> ResponseEntity.notFound().build());
//    }

    public StateMachine<Status, StatusEvent> build(final Task task) {
        var orderDb = this.taskRepository.findById(task.getId());
        var stateMachine = this.stateMachineFactory.getStateMachine(task.getId().toString());
        log.warn("stateMachine   {}" ,stateMachine);
        log.warn("orderDb   {}" ,orderDb);
        stateMachine.stop();
        stateMachine.getStateMachineAccessor()
                .doWithAllRegions(sma -> {
                    sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
                        @Override
                        public void preStateChange(State<Status, StatusEvent> state, Message<StatusEvent> message, Transition<Status,
                                StatusEvent> transition, StateMachine<Status, StatusEvent> stateMachine, StateMachine<Status, StatusEvent> rootStateMachine) {
                            var orderId = Long.class.cast(message.getHeaders().get("taskId"));
                            var taskOptional = taskRepository.findById(orderId);
                            if (taskOptional.isPresent() ) {

                                taskOptional.get().setState(state.getId().name());
                                taskRepository.save(taskOptional.get());

                            }
                        }
                    });

                    sma.resetStateMachine(new DefaultStateMachineContext<>(Status.valueOf(orderDb.get().getState()), null, null, null));
                });

        stateMachine.start();
        return stateMachine;

    }

}














