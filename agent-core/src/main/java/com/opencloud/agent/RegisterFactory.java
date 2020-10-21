package com.opencloud.agent;


import com.opencloud.config.exception.AgentEngineException;

public interface RegisterFactory {

    AgentServer create(String sourceName, String type) throws AgentEngineException;

    Class<? extends AgentServer> getClass(String type) throws AgentEngineException;
}
