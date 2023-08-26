package com.shpp.rstefanyshyn.spring.services;

import lombok.extern.slf4j.Slf4j;
import org.junit.jupiter.api.Test;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.StateMachineBuilder;
import org.springframework.statemachine.test.StateMachineTestPlan;
import org.springframework.statemachine.test.StateMachineTestPlanBuilder;

import static org.hamcrest.CoreMatchers.not;
import static org.hamcrest.collection.IsMapContaining.*;


@Slf4j
public class TaskPlannedWorkNotifiedDone {


    public TaskPlannedWorkNotifiedDone() throws Exception {
    }

    private StateMachine<String, String> buildMachine() throws Exception {
        StateMachineBuilder.Builder<String, String> builder = StateMachineBuilder.builder();

        builder.configureConfiguration()
                .withConfiguration()
                .autoStartup(true);

        builder.configureStates()
                .withStates()
                .initial(String.valueOf((StatusTest.PLANNED)))
                .state(String.valueOf(StatusTest.WORK_IN_PROGRESS))
                .state(String.valueOf(StatusTest.POSTPONED))
                .state(String.valueOf(StatusTest.NOTIFIED))
                .state(String.valueOf(StatusTest.SIGNED))
                .state(String.valueOf(StatusTest.CANCELLED))
                .state(String.valueOf(StatusTest.DONE));

        builder.configureTransitions()
                .withExternal()
                .source(String.valueOf(StatusTest.PLANNED))
                .target(String.valueOf(StatusTest.WORK_IN_PROGRESS))
                .event(String.valueOf(EventTest.START))

                .and()
                .withExternal()
                .source(String.valueOf(StatusTest.WORK_IN_PROGRESS))
                .target(String.valueOf(StatusTest.NOTIFIED))
                .event(String.valueOf(EventTest.NOTIFY))
                .and()
                .withExternal()
                .source(String.valueOf(StatusTest.NOTIFIED))
                .target(String.valueOf(StatusTest.DONE))
                .event(String.valueOf(EventTest.COMPLETE))

                .action(b -> {
                    b.getExtendedState().getVariables().put("key1", "value1");
                });

        return builder.build();
    }

    @Test
    void test() throws Exception {
        StateMachine<String, String> machine = buildMachine();
        StateMachineTestPlan<String, String> plan =
                StateMachineTestPlanBuilder.<String, String>builder()
                        .defaultAwaitTime(2)
                        .stateMachine(machine)
                        .step()
                        .expectStates(String.valueOf((StatusTest.PLANNED)))
                        .and()
                        .step()
                        .sendEvent(String.valueOf(EventTest.START))
                        .expectStateChanged(1)
                        .expectStates(String.valueOf(StatusTest.WORK_IN_PROGRESS))
                        .and()
                        .step()
                        .expectStates(String.valueOf((StatusTest.WORK_IN_PROGRESS)))
                        .and()
                        .step()
                        .sendEvent(String.valueOf(EventTest.NOTIFY))
                        .expectStateChanged(1)
                        .expectStates(String.valueOf(StatusTest.NOTIFIED))
                        .and()
                        .step()
                        .expectStates(String.valueOf((StatusTest.NOTIFIED)))
                        .and()
                        .step()
                        .sendEvent(String.valueOf(EventTest.COMPLETE))
                        .expectStateChanged(1)
                        .expectStates(String.valueOf(StatusTest.DONE))

                        .expectVariable("key1")
                        .expectVariable("key1", "value1")
                        .expectVariableWith(hasKey("key1"))
                        .expectVariableWith(hasValue("value1"))
                        .expectVariableWith(hasEntry("key1", "value1"))
                        .expectVariableWith(not(hasKey("key2")))
                        .and()
                        .build();

        plan.test();
    }

}