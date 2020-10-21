package com.opencloud.agent.config;

import com.google.common.collect.ImmutableMap;
import com.opencloud.agent.AgentServer;

import java.util.HashMap;
import java.util.Map;

/**
 * @author xielianjun
 */
public class SimpleMaterializedConfiguration implements MaterializedConfiguration {
    private final Map<String, AgentServer> agentRunnerMap;

    public SimpleMaterializedConfiguration() {
        this.agentRunnerMap = new HashMap<>();
    }

    @Override
    public void addServerRunner(String agentName, AgentServer agentServer) {
        agentRunnerMap.put(agentName, agentServer);
    }

    @Override
    public ImmutableMap<String, AgentServer> getAgentServers() {
        return ImmutableMap.copyOf(agentRunnerMap);
    }
}
