package com.opencloud.config;

import com.google.common.base.Preconditions;
import com.opencloud.agent.BasicConfigurationConstants;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.Map.Entry;

import static com.opencloud.agent.BasicConfigurationConstants.AGENTN_NAME;
import static com.opencloud.agent.BasicConfigurationConstants.CONFIG_SERVER_NAME;
import static com.opencloud.config.EngineConfigurationErrorType.*;

public class AgentEngineConfiguration {

    public static final String INDENTSTEP = "  ";
    public static final String NEWLINE = System.getProperty("line.separator", "\n");
    private static final Logger LOGGER = LoggerFactory.getLogger(AgentEngineConfiguration.class);
    private static AgentConfiguration agentConfiguration;

    public AgentEngineConfiguration(Map<String, String> properties) {
        if (agentConfiguration != null) {
            agentConfiguration.clear();
        }
        for (Entry<String, String> entry : properties.entrySet()) {
            if (!addRawProperty(entry.getKey(), entry.getValue())) {
                LOGGER.warn("Configuration property ignored: {} = {}", entry.getKey(), entry.getValue());
            }
        }
        validateConfiguration();
    }

    public AgentConfiguration getAgentConfiguration() {
        return agentConfiguration;
    }

    private void validateConfiguration() {
        AgentConfiguration aconf = agentConfiguration;
        if (!aconf.isValid()) {
            LOGGER.warn("Agent configuration invalid for agent '{}'. It will be removed.", AGENTN_NAME);
            LOGGER.error(AGENT_CONFIGURATION_INVALID.getError());
        }
        LOGGER.info("Post-validation agent configuration contains configuration for agents: {}", aconf);
    }

    private boolean addRawProperty(String rawName, String rawValue) {
        if (rawName == null || rawValue == null) {
            LOGGER.error(AGENT_NULL_ATTR.getError());
            return false;
        }
        String name = rawName.trim();
        String value = rawValue.trim();

        if (value.isEmpty()) {
            LOGGER.error(PROPERTY_VALUE_NULL.getError());
            return false;
        }

        if (agentConfiguration == null) {
            agentConfiguration = new AgentConfiguration();
        }
        AgentConfiguration aconf = agentConfiguration;
        return aconf.addProperty(name, value);
    }

    public static class AgentConfiguration {
        private Context serverContext;

        public Context getServerContext() {
            return serverContext;
        }

        private boolean isValid() {
            LOGGER.info("Initial configuration: {}", getPrevalidationConfig());
            validateRegister();
            return true;
        }

        private void validateRegister() {
            Preconditions.checkNotNull(serverContext, "Agent configuration does not contain any valid register. Marking it as invalid.");
            String serverName = serverContext.getString(CONFIG_SERVER_NAME);
            Preconditions.checkNotNull(serverName, "Agent configuration does not contain any valid [agent.name] property. Marking it as invalid.");
            BasicConfigurationConstants.setAgentnName(serverName);
        }

        private String getPrevalidationConfig() {
            return "AgentConfiguration[" + AGENTN_NAME + "]" + NEWLINE +
                    "Server: " + serverContext + NEWLINE;
        }

        private boolean addProperty(String key, String value) {
            if (addAsServerConfig(key, value)) {
                return true;
            }
            LOGGER.error("Invalid property specified: {}", INVALID_PROPERTY.getError());
            return false;
        }


        private boolean addAsServerConfig(String key, String value) {
            if (key == null) {
                return false;
            }
            String name = key.trim();
            LOGGER.info("Processing addComponentConfig:{}={}", name, value);

            if (serverContext == null) {
                LOGGER.debug("Created context for {}: {}", name, value);
                serverContext = new Context();
                serverContext.put(name, value);
            }
            serverContext.put(name, value);
            return true;
        }

        public void clear() {
            if (serverContext != null) {
                serverContext.clear();
            }
            serverContext = null;
        }
    }
}
