package com.opencloud.agent.task;

import com.opencloud.agent.lifecycle.LifecycleState;
import com.opencloud.agent.sink.ParserRunner;
import com.opencloud.agent.sink.PollableParserRunner;

public class CommandFactory {

    private static ParserRunner defaultCommandRunner;

    static {
        defaultCommandRunner = new PollableParserRunner();
        if (!defaultCommandRunner.getLifecycleState().equals(LifecycleState.START)) {
            defaultCommandRunner.start();
        }
    }

    public static ParserRunner getDefaultCommandRunner() {
        return defaultCommandRunner;
    }
}
