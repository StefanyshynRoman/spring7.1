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
public class TestPlannedPostponedSignedCanceled {


    public TestPlannedPostponedSignedCanceled() throws Exception {
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
                .target(String.valueOf(StatusTest.POSTPONED))
                .event(String.valueOf(EventTest.POSTPONE))

                .and()
                .withExternal()
                .source(String.valueOf(StatusTest.POSTPONED))
                .target(String.valueOf(StatusTest.SIGNED))
                .event(String.valueOf(EventTest.SIGN))
                .and()
                .withExternal()
                .source(String.valueOf(StatusTest.SIGNED))
                .target(String.valueOf(StatusTest.CANCELLED))
                .event(String.valueOf(EventTest.CANCEL))

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
                        .sendEvent(String.valueOf(EventTest.POSTPONE))
                        .expectStateChanged(1)
                        .expectStates(String.valueOf(StatusTest.POSTPONED))
                        .and()
                        .step()
                        .expectStates(String.valueOf((StatusTest.POSTPONED)))
                        .and()
                        .step()
                        .sendEvent(String.valueOf(EventTest.SIGN))
                        .expectStateChanged(1)
                        .expectStates(String.valueOf(StatusTest.SIGNED))
                        .and()
                        .step()
                        .expectStates(String.valueOf((StatusTest.SIGNED)))
                        .and()
                        .step()
                        .sendEvent(String.valueOf(EventTest.CANCEL))
                        .expectStateChanged(1)
                        .expectStates(String.valueOf(StatusTest.CANCELLED))

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