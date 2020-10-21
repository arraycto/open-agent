package com.opencloud.agent.lifecycle;

public interface LifecycleAware {

    void start();

    void stop();

    LifecycleState getLifecycleState();

}
