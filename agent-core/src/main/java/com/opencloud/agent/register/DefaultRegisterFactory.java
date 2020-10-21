package com.opencloud.agent.register;

import com.google.common.base.Preconditions;
import com.opencloud.agent.AgentServer;
import com.opencloud.agent.RegisterFactory;
import com.opencloud.config.RegisterType;
import com.opencloud.config.exception.AgentEngineException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Locale;

public class DefaultRegisterFactory implements RegisterFactory {

    private static final Logger logger = LoggerFactory.getLogger(DefaultRegisterFactory.class);

    @Override
    public AgentServer create(String name, String type) throws AgentEngineException {
        Preconditions.checkNotNull(name, "name");
        Preconditions.checkNotNull(type, "type");
        logger.info("Creating instance of register {}, type {}", name, type);
        Class<? extends AgentServer> registerClass = getClass(type);
        try {
            AgentServer agentServer = registerClass.newInstance();
            agentServer.setName(name);
            agentServer.setType(type);
            return agentServer;
        } catch (Exception ex) {
            throw new AgentEngineException("Unable to create register: " + name + ", type: " + type + ", class: " + registerClass.getName(), ex);
        }
    }

    @SuppressWarnings("unchecked")
    @Override
    public Class<? extends AgentServer> getClass(String type) throws AgentEngineException {
        String registerClassName = type;
        RegisterType srcType = RegisterType.OTHER;
        try {
            srcType = RegisterType.valueOf(type.toUpperCase(Locale.ENGLISH));
        } catch (IllegalArgumentException ex) {
            logger.debug("Register type {} is a custom type", type);
        }
        if (!srcType.equals(RegisterType.OTHER)) {
            registerClassName = srcType.getClassName();
        }
        try {
            return (Class<? extends AgentServer>) Class.forName(registerClassName);
        } catch (Exception ex) {
            throw new AgentEngineException("Unable to load register type: " + type + ", class: " + registerClassName, ex);
        }
    }
}
