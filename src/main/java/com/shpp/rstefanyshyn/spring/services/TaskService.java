package com.shpp.rstefanyshyn.spring.services;

import com.shpp.rstefanyshyn.spring.model.Task;
import com.shpp.rstefanyshyn.spring.repository.TaskRepository;
import com.shpp.rstefanyshyn.spring.statemachine.Status;
import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.support.DefaultStateMachineContext;
import org.springframework.stereotype.Service;
@Service
public class TaskService {

    private final StateMachine<Status, StatusEvent> stateMachine;
    private final TaskRepository taskRepository;
    public TaskService(StateMachineFactory<Status, StatusEvent> stateMachineFactory, TaskRepository taskRepository) {
        this.stateMachine = stateMachineFactory.getStateMachine();
        this.taskRepository = taskRepository;
    }

    // ... інші методи

//    public void startTask(Long taskId) {
//        // Зчитуємо завдання з бази даних за ID
//        Task task = taskRepository.findById(taskId).orElse(null);
//
//        if (task != null) {
//            // Встановлюємо поточний стан автомата відповідно до статусу завдання
//            stateMachine.getStateMachineAccessor().doWithAllRegions(sma -> {
//                sma.resetStateMachine(new DefaultStateMachineContext<>(task.getState(), null, null, null));
//            });
//
//            // Відправляємо подію START для зміни статусу
//            stateMachine.sendEvent(StatusEvent.START);
//
//            // Змінюємо статус завдання в базі даних
//            task.setState(String.valueOf(stateMachine.getState().getId()));
//            taskRepository.save(task);
//        }
//    }

    // ... інші методи
}
