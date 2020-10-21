package com.opencloud.agent.sink;

import com.opencloud.agent.lifecycle.LifecycleAware;
import com.opencloud.config.Configurable;


public interface ParserProcessor extends LifecycleAware, Configurable {
    Status process();

    enum Status {
        READY, BACKOFF
    }
}
