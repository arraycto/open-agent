package com.opencloud.config.exception;

/**
 * Base class of all flume exceptions.
 */
public class AgentEngineException extends RuntimeException {

    public AgentEngineException(String msg) {
        super(msg);
    }

    public AgentEngineException(String msg, Throwable th) {
        super(msg, th);
    }

    public AgentEngineException(Throwable th) {
        super(th);
    }

}
