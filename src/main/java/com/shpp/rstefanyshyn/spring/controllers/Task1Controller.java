//package com.shpp.rstefanyshyn.spring.controllers;
//
//import com.shpp.rstefanyshyn.spring.model.Task1;
//import com.shpp.rstefanyshyn.spring.repository.Task1Repository;
//import com.shpp.rstefanyshyn.spring.statemachine.Status;
//import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
//import lombok.RequiredArgsConstructor;
//import org.springframework.hateoas.CollectionModel;
//import org.springframework.hateoas.EntityModel;
//import org.springframework.messaging.Message;
//import org.springframework.messaging.support.MessageBuilder;
//import org.springframework.statemachine.StateMachine;
//import org.springframework.statemachine.config.StateMachineFactory;
//import org.springframework.statemachine.state.State;
//import org.springframework.statemachine.support.DefaultStateMachineContext;
//import org.springframework.statemachine.support.StateMachineInterceptorAdapter;
//import org.springframework.statemachine.transition.Transition;
//import org.springframework.web.bind.annotation.*;
//
//import java.time.LocalDate;
//import java.util.List;
//
//@RestController
//@RequiredArgsConstructor
//public class Task1Controller {
//
//
//    private final Task1Repository task1Repository;
//
//    private final StateMachineFactory<Status, StatusEvent> stateMachineFactory;
//
//    @GetMapping("/task")
//    public List<Task1> all() {
//        return task1Repository.findAll();
//    }
//
//    @PostMapping("/createOrder")
//    public Task1 createOrder() {
//        Task1 task1 = new Task1();
//        task1.setState(Status.PLANNED.name());
//        task1.setLocalDate(LocalDate.now());
//        return task1Repository.save(task1);
//    }
//
//    @PutMapping("/change")
//    public String changeState(@RequestBody Task1 task1) {
//
//        //making the machine in current state of the task1
//        StateMachine<Status, StatusEvent> sm = build(task1);
//        sm.getExtendedState().getVariables().put("paymentType", task1.getPaymentType());
//        sm.sendEvent(
//                MessageBuilder.withPayload(StatusEvent.valueOf(task1.getEvent()))
//                        .setHeader("orderId", task1.getId())
//                        .setHeader("state", task1.getState())
//                        .build()
//        );
//        return "state changed";
//    }
//
//    //    public StateMachine<Status,StatusEvent> build(final Task1 task1Dto){
////        var orderDb =  this.task1Repository.findById(task1Dto.getId());
////        var stateMachine =  this.stateMachineFactory.getStateMachine(task1Dto.getId().toString());
////        stateMachine.stop();
////        stateMachine.getStateMachineAccessor()
////                .doWithAllRegions(sma -> {
////                    sma.resetStateMachine(new DefaultStateMachineContext<>(Status.valueOf(orderDb.get().getState()), null, null, null));
////
////                });
////        stateMachine.start();
////        return stateMachine;
////    }
//    public StateMachine<Status, StatusEvent> build(final Task1 task1) {
//        var orderDb = this.task1Repository.findById(task1.getId());
//        var stateMachine = this.stateMachineFactory.getStateMachine(task1.getId().toString());
//        stateMachine.stop();
//        stateMachine.getStateMachineAccessor()
//                .doWithAllRegions(sma -> {
//                    sma.addStateMachineInterceptor(new StateMachineInterceptorAdapter<>() {
//                        @Override
//                        public void preStateChange(State<Status, StatusEvent> state, Message<StatusEvent> message, Transition<Status, StatusEvent> transition,
//                                                   StateMachine<Status, StatusEvent> stateMachine, StateMachine<Status, StatusEvent> rootStateMachine) {
//                            var orderId = Long.class.cast(message.getHeaders().get("orderId"));
//                            var order = task1Repository.findById(orderId);
//                            if (order.isPresent()) {
//                                order.get().setState(state.getId().name());
//                                task1Repository.save(order.get());
//                            }
//                        }
//                    });
//                    sma.resetStateMachine(new DefaultStateMachineContext<>(Status.valueOf(orderDb.get().getState()), null, null, null));
//                });
//
//        stateMachine.start();
//        return stateMachine;
//
//    }
//}
//@PutMapping("/tasks/{id}/status")
//public ResponseEntity<Task> changeState(@RequestBody Task task1) {
//        Task existingTask = taskRepository.findById(task1.getId())
//        .orElseThrow(() -> new TaskNotFoundException(task1.getId()));
//
//        log.info("TASK____________{}", task1);
//
//        if (existingTask.getState().equals(task1.getState())) {
//        log.info("State is already the same.");
//        return ResponseEntity.badRequest().build();
//        }
//
//        //making the machine in the current state of the task1
//        StateMachine<Status, StatusEvent> sm = build(task1);
//
//        sm.getExtendedState().getVariables().put("extendedState", task1.getExtendedState());
//
//        sm.sendEvent(
//        MessageBuilder.withPayload(StatusEvent.valueOf(task1.getEvent()))
//        .setHeader("taskId", task1.getId())
//        .setHeader("state", task1.getState())
//        .build()
//        );
//
//        return ResponseEntity.status(HttpStatus.CREATED).body(task1);
//        }