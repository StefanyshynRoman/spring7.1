package com.shpp.rstefanyshyn.spring.services;

import com.shpp.rstefanyshyn.spring.controllers.TaskController;
import com.shpp.rstefanyshyn.spring.exeption.InvalidStatusException;
import com.shpp.rstefanyshyn.spring.exeption.TaskNotFoundException;
import com.shpp.rstefanyshyn.spring.model.Task;
import com.shpp.rstefanyshyn.spring.repository.TaskRepository;
import com.shpp.rstefanyshyn.spring.statemachine.Status;
import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.hateoas.CollectionModel;
import org.springframework.hateoas.EntityModel;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.messaging.Message;
import org.springframework.messaging.support.MessageBuilder;
import org.springframework.security.core.Authentication;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.state.State;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
import org.springframework.statemachine.transition.Transition;
import org.springframework.stereotype.Service;
import org.springframework.web.bind.annotation.PathVariable;

import java.util.List;
import java.util.Locale;
import java.util.ResourceBundle;
import java.util.stream.Collectors;

import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.linkTo;
import static org.springframework.hateoas.server.mvc.WebMvcLinkBuilder.methodOn;

@Slf4j
@Service
public class TaskService {
    TaskModelAssembler assembler;

    TaskRepository taskRepository;
    StateMachineFactory<Status, StatusEvent> stateMachineFactory;

    @Autowired
    public TaskService(TaskModelAssembler assembler,
                       TaskRepository taskRepository,
                       StateMachineFactory<Status, StatusEvent> stateMachineFactory) {
        this.assembler = assembler;
        this.taskRepository = taskRepository;
        this.stateMachineFactory = stateMachineFactory;
    }

    public TaskService() {
    }

    public ResponseEntity<Task> changeState(Task task1, Locale locale) {
        ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        StateMachine<Status, StatusEvent> sm = build(task1);
        sm.getExtendedState().getVariables().put("extendedState", task1.getExtendedState());

        StateMachine<Status, StatusEvent> stateMachine1 = build(task1);
        try {
            sm.sendEvent(
                    MessageBuilder.withPayload(StatusEvent.valueOf(task1.getEvent()))
                            .setHeader("taskId", task1.getId())
                            .setHeader("state", task1.getState())
                            .build()
            );
        } catch (Exception e){
            throw new InvalidStatusException(task1.getEvent().toString()+" - "+
                 bundle.getString("invalidStatusMessage"));
                 //   " is invalid event, please  chose:  START, POSTPONE, NOTIFY, SIGN, COMPLETE, CANCEL");
        }

        if (sm.getState().getId().equals(stateMachine1.getState().getId())) {
            String errorMessage = "Invalid event " + StatusEvent.valueOf(task1.getEvent()) +
                    "  " + "your status id " + sm.getState().getId().toString();
            log.warn(errorMessage);
            throw new InvalidStatusException(errorMessage);
        }
        return ResponseEntity.status(HttpStatus.CREATED).body(task1);
    }

    public StateMachine<Status, StatusEvent> build(Task task) {
        var orderDb = this.taskRepository.findById(task.getId());
        var stateMachine = this.stateMachineFactory.getStateMachine(task.getId().toString());

        stateMachine.stop();
        stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
            sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
                @Override
                public void preStateChange(State<Status, StatusEvent> state, Message<StatusEvent> message, Transition<Status,
                        StatusEvent> transition, StateMachine<Status, StatusEvent> stateMachine, StateMachine<Status, StatusEvent> rootStateMachine) {
                    var orderId = Long.class.cast(message.getHeaders().get("taskId"));
                    var taskOptional = taskRepository.findById(orderId);
                    if (taskOptional.isPresent()) {
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

    public CollectionModel<EntityModel<Task>> getAll() {

        List<EntityModel<Task>> task = taskRepository.findAll().stream()
                .map(assembler::toModel)
                .collect(Collectors.toList());

        return CollectionModel.of(task, linkTo(methodOn(TaskController.class).all()).withSelfRel());
    }

    public EntityModel<Task> getOne(@PathVariable Long id) {
     //   ResourceBundle bundle = ResourceBundle.getBundle("messages", locale);

        Task task = taskRepository.findById(id) //
                .orElseThrow(() -> new TaskNotFoundException(id,""+"notFoundTaskMessages"));

        return assembler.toModel(task);
    }

}