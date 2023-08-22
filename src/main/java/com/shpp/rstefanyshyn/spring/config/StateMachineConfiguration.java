//package com.shpp.rstefanyshyn.spring.config;
//
//import com.shpp.rstefanyshyn.spring.statemachine.Status;
//import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
//import lombok.extern.slf4j.Slf4j;
//import org.springframework.context.annotation.Configuration;
//import org.springframework.statemachine.config.EnableStateMachineFactory;
//import org.springframework.statemachine.config.StateMachineConfigurerAdapter;
//import org.springframework.statemachine.config.builders.StateMachineConfigurationConfigurer;
//import org.springframework.statemachine.config.builders.StateMachineStateConfigurer;
//import org.springframework.statemachine.config.builders.StateMachineTransitionConfigurer;
//
//import java.util.EnumSet;
//
//@Slf4j
//@Configuration
//@EnableStateMachineFactory
//public class StateMachineConfiguration extends StateMachineConfigurerAdapter<Status, StatusEvent> {
//
//    @Override
//    public void configure(StateMachineStateConfigurer<Status, StatusEvent> states) throws Exception {
//        states
//                .withStates().
//                initial(Status.PLANNED)
//                .states(EnumSet.allOf(Status.class))
//                .end(Status.DONE)
//                .end(Status.CANCELLED);
//    }
//    @Override
//    public void configure(StateMachineConfigurationConfigurer<Status, StatusEvent> config) throws Exception {
//        config.withConfiguration()
//                .autoStartup(false)
//                .listener(new StateMachineListener());
//    }
//
//    @Override
//    public void configure(StateMachineTransitionConfigurer<Status, StatusEvent> transitions) throws Exception {
//        transitions
//                .withExternal()
//                .source(Status.PLANNED).target(Status.WORK_IN_PROGRESS).event(StatusEvent.START)
//                .action(ctx -> {
//                    log.info("This PLANNED -> WORK_IN_PROGRESS");
//                })
//                .and()
//                .withExternal()
//                .source(Status.PLANNED).target(Status.POSTPONED).event(StatusEvent.POSTPONE)
//                .action(ctx -> {
//                    log.info("This PLANNED ->POSTPONED");
//                })
//                .and()
//                .withExternal()
//                .source(Status.PLANNED).target(Status.CANCELLED).event(StatusEvent.CANCEL)
//                .action(ctx -> {
//                    log.info("This PLANNED-> CANCELLED");
//                })
//                .and()
//
//                .withExternal()
//                .source(Status.WORK_IN_PROGRESS).target(Status.NOTIFIED).event(StatusEvent.NOTIFY)
//                .action(ctx -> {
//                    log.info("This WORK_IN_PROGRESS-> NOTiFY ");
//                })
//                .and()
//                .withExternal()
//                .source(Status.WORK_IN_PROGRESS).target(Status.SIGNED).event(StatusEvent.SIGN)
//                .action(ctx -> {
//                    log.info("This WORK_IN_PROGRESS-> SIGN ");
//                })
//                .and()
//                .withExternal()
//                .source(Status.WORK_IN_PROGRESS).target(Status.CANCELLED).event(StatusEvent.CANCEL)
//                .action(ctx -> {
//                    log.info("This WORK_IN_PROGRESS-> CANCELLED ");
//                })
//                .and()
//
//                .withExternal()
//                .source(Status.POSTPONED).target(Status.NOTIFIED).event(StatusEvent.NOTIFY)
//                .action(ctx -> {
//                    log.info("This POSTPONED-> NOTIFIED ");
//                })
//                .and()
//                .withExternal()
//                .source(Status.POSTPONED).target(Status.SIGNED).event(StatusEvent.SIGN)
//                .action(ctx -> {
//                    log.info("This POSTPONED-> SIGNED");
//                })
//                .and()
//                .withExternal()
//                .source(Status.POSTPONED).target(Status.CANCELLED).event(StatusEvent.CANCEL)
//                .action(ctx -> {
//                    log.info("This POSTPONED ->CANCELLED");
//                })
//                .and()
//
//                .withExternal()
//                .source(Status.NOTIFIED).target(Status.DONE).event(StatusEvent.COMPLETE)
//                .action(ctx -> {
//                    log.info("This NOTIFIED-> DONE");
//                })
//                .and()
//                .withExternal()
//                .source(Status.NOTIFIED).target(Status.CANCELLED).event(StatusEvent.CANCEL)
//                .action(ctx -> {
//                    log.info("This NOTIFIED-> CANCELLED");
//                })
//                .and()
//
//                .withExternal()
//                .source(Status.SIGNED).target(Status.DONE).event(StatusEvent.COMPLETE)
//                .action(ctx -> {
//                    log.info("This NOTIFIED-> DONE");
//                })
//                .and()
//                .withExternal()
//                .source(Status.SIGNED).target(Status.CANCELLED).event(StatusEvent.CANCEL)
//          .action(ctx -> {
//            log.info("This SIGNED -> CANCELLED");
//        });
//    }
//
//}