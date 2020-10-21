package com.opencloud.agent.config;

import com.google.common.collect.ImmutableMap;
import com.opencloud.agent.AgentServer;

public interface MaterializedConfiguration {

    void addServerRunner(String agentnName, AgentServer agentServer);

    ImmutableMap<String, AgentServer> getAgentServers();
}
