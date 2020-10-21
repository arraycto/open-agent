package com.opencloud.agent.executor;

import org.springframework.context.ApplicationEvent;

import java.util.List;

public class ExecutorEvent<T> extends ApplicationEvent {

    public ExecutorEvent(List<ExecutorFromBrower> info) {
        super(info);
    }


    @Override
    public T getSource() {
        return (T) super.getSource();
    }
}
