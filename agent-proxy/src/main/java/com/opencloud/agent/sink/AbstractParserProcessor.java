package com.opencloud.agent.sink;

import com.opencloud.agent.lifecycle.LifecycleState;
import com.opencloud.config.Configurable;

import java.net.InetSocketAddress;
import java.util.List;

public abstract class AbstractParserProcessor implements ParserProcessor, Configurable {

    private LifecycleState lifecycleState;

    private Integer collectType;

    public AbstractParserProcessor() {
        lifecycleState = LifecycleState.IDLE;
    }

    public Integer getCollectType() {
        return collectType;
    }

    public void setCollectType(Integer collectType) {
        this.collectType = collectType;
    }

    public abstract void put(SourceMessage message);


    @Override
    public synchronized void start() {

        lifecycleState = LifecycleState.START;
    }

    @Override
    public synchronized void stop() {
        lifecycleState = LifecycleState.STOP;
    }

    @Override
    public synchronized LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    public abstract void puts(List<SourceMessage> messages);

    public abstract void removeProxy(InetSocketAddress clientAddress);
}
