package com.opencloud.agent.sink;

import com.opencloud.agent.lifecycle.LifecycleAware;
import com.opencloud.agent.lifecycle.LifecycleState;

public abstract class ParserRunner implements LifecycleAware {
    private AbstractParserProcessor policy;
    private LifecycleState lifecycleState;

    public ParserRunner() {
        lifecycleState = LifecycleState.IDLE;
    }

    public AbstractParserProcessor getPolicy() {
        return policy;
    }

    public void setPolicy(AbstractParserProcessor policy) {
        this.policy = policy;
    }

    @Override
    public void start() {
        lifecycleState = LifecycleState.START;
    }

    @Override
    public void stop() {
        lifecycleState = LifecycleState.STOP;
    }

    @Override
    public LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    public abstract void put(SourceMessage sourceMessage);
}