package com.opencloud.agent;

import com.opencloud.agent.lifecycle.LifecycleAware;
import com.opencloud.agent.lifecycle.LifecycleState;
import com.opencloud.config.annotations.InterfaceAudience;
import com.opencloud.config.annotations.InterfaceStability;

@InterfaceAudience.Public
@InterfaceStability.Stable
public abstract class AgentServer implements LifecycleAware, OriginalComponent {

    private LifecycleState lifecycleState;

    public AgentServer() {
        lifecycleState = LifecycleState.IDLE;
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

    @Override
    public String getName() {
        return null;
    }

    @Override
    public void setName(String name) {

    }

    @Override
    public String getType() {
        return null;
    }

    @Override
    public void setType(String type) {

    }

}
