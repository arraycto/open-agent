package com.opencloud.agent.config;

import cn.hutool.core.util.StrUtil;
import com.google.common.collect.Maps;
import com.opencloud.agent.AgentServer;
import com.opencloud.agent.BasicConfigurationConstants;
import com.opencloud.agent.Configurables;
import com.opencloud.agent.RegisterFactory;
import com.opencloud.agent.register.DefaultRegisterFactory;
import com.opencloud.config.AgentEngineConfiguration;
import com.opencloud.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Enumeration;
import java.util.Map;
import java.util.Properties;

public abstract class AbstractConfigurationProvider implements ConfigurationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(AbstractConfigurationProvider.class);
    private final RegisterFactory registerFactory;
    private String agentName;


    public AbstractConfigurationProvider() {
        super();
        this.registerFactory = new DefaultRegisterFactory();
    }

    protected abstract AgentEngineConfiguration getAgentEngineConfiguration();

    public String getAgentName() {
        return agentName;
    }

    protected Map<String, String> toMap(Properties properties) {
        Map<String, String> result = Maps.newHashMap();
        Enumeration<?> propertyNames = properties.propertyNames();
        while (propertyNames.hasMoreElements()) {
            String name = (String) propertyNames.nextElement();
            String value = properties.getProperty(name);
            result.put(name, value);
        }
        return result;
    }


    @Override
    public MaterializedConfiguration getConfiguration() {
        MaterializedConfiguration conf = new SimpleMaterializedConfiguration();
        AgentEngineConfiguration fconfig = getAgentEngineConfiguration();
        AgentEngineConfiguration.AgentConfiguration agentConf = fconfig.getAgentConfiguration();
        if (agentConf != null) {
            try {
                loadRegisters(conf, agentConf);
            } catch (InstantiationException ex) {
                LOGGER.error("Failed to instantiate component", ex);
            }
        } else {
            LOGGER.warn("No configuration found for this host:{}", getAgentName());
        }
        return conf;
    }

    private void loadRegisters(MaterializedConfiguration conf, AgentEngineConfiguration.AgentConfiguration agentConf) throws InstantiationException {
        Context context = agentConf.getServerContext();
        if (context != null) {
            agentName = StrUtil.blankToDefault(context.getString(BasicConfigurationConstants.CONFIG_SERVER_NAME), BasicConfigurationConstants.AGENTN_NAME);
            String registerType = context.getString(BasicConfigurationConstants.CONFIG_TYPE);
            AgentServer agentServer = registerFactory.create(agentName, registerType);
            try {
                Configurables.configure(agentServer, context);
            } catch (Exception e) {
                String msg = String.format("Register %s has been removed due to an " + "error during configuration", agentName);
                LOGGER.error(msg, e);
            }
            conf.addServerRunner(BasicConfigurationConstants.AGENTN_NAME, agentServer);
        }
    }
}