package com.shpp.rstefanyshyn.spring.config;

import com.shpp.rstefanyshyn.spring.statemachine.Status;
import com.shpp.rstefanyshyn.spring.statemachine.StatusEvent;
import lombok.extern.slf4j.Slf4j;
import org.springframework.statemachine.listener.StateMachineListenerAdapter;
import org.springframework.statemachine.state.State;
@Slf4j
public class StateMachineListener extends StateMachineListenerAdapter<Status, StatusEvent> {
    @Override
    public void stateChanged(State<Status, StatusEvent> from, State<Status, StatusEvent> to) {
        log.info("state changed from " + from.toString() + "        to" + to.toString());
    }
}