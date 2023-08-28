package com.shpp.rstefanyshyn.spring.config;

import com.shpp.rstefanyshyn.spring.statemachine.Status;
import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.statemachine.StateMachine;
import org.springframework.statemachine.config.EnableStateMachineFactory;
import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
import org.springframework.statemachine.config.StateMachineFactory;
import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;

import java.util.EnumSet;

@Configuration
@EnableStateMachineFactory

public class StateMachineConfig extends StateMachineConfigurerAdapter<Status, StatusEvent> {

    @Override
    public void configure(StateMachineStateConfigurer<Status, StatusEvent> states) throws Exception {
        states
                .withStates()
                .initial(Status.PLANNED)
                .states(EnumSet.allOf(Status.class));
    }

    @Override
    public void configure(StateMachineConfigurationConfigurer<Status, StatusEvent> config) throws Exception {
        config.withConfiguration()
                .autoStartup(false)
                .listener(new StateMachineListener());
    }

    @Override
    public void configure(StateMachineTransitionConfigurer<Status, StatusEvent> transitions) throws Exception {
        transitions
                .withExternal()
                .source(Status.PLANNED).target(Status.WORK_IN_PROGRESS).event(StatusEvent.START)
                .and()
                .withExternal()
                .source(Status.PLANNED).target(Status.POSTPONED).event(StatusEvent.POSTPONE)
                .and()
                .withExternal()
                .source(Status.PLANNED).target(Status.CANCELLED).event(StatusEvent.CANCEL)
                .and()

                .withExternal()
                .source(Status.WORK_IN_PROGRESS).target(Status.NOTIFIED).event(StatusEvent.NOTIFY)
                .and()
                .withExternal()
                .source(Status.WORK_IN_PROGRESS).target(Status.SIGNED).event(StatusEvent.SIGN)
                .and()
                .withExternal()
                .source(Status.WORK_IN_PROGRESS).target(Status.CANCELLED).event(StatusEvent.CANCEL)
                .and()

                .withExternal()
                .source(Status.POSTPONED).target(Status.NOTIFIED).event(StatusEvent.NOTIFY)
                .and()
                .withExternal()
                .source(Status.POSTPONED).target(Status.SIGNED).event(StatusEvent.SIGN)
                .and()
                .withExternal()
                .source(Status.POSTPONED).target(Status.CANCELLED).event(StatusEvent.CANCEL)
                .and()

                .withExternal()
                .source(Status.NOTIFIED).target(Status.DONE).event(StatusEvent.COMPLETE)
                .and()
                .withExternal()
                .source(Status.NOTIFIED).target(Status.CANCELLED).event(StatusEvent.CANCEL)
                .and()

                .withExternal()
                .source(Status.SIGNED).target(Status.DONE).event(StatusEvent.COMPLETE)
                .and()
                .withExternal()
                .source(Status.SIGNED).target(Status.CANCELLED).event(StatusEvent.CANCEL);


    }

    @Bean
    public StateMachine<Status, StatusEvent> stateMachine(StateMachineFactory<Status, StatusEvent> stateMachineFactory) {
        return stateMachineFactory.getStateMachine();
    }
}
