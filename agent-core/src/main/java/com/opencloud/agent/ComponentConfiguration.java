package com.opencloud.agent;

import com.opencloud.config.AgentEngineConfiguration;
import com.opencloud.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import javax.naming.ConfigurationException;

import static com.opencloud.config.EngineConfigurationErrorType.ATTRS_MISSING;

public abstract class ComponentConfiguration {

    private static final Logger LOGGER = LoggerFactory.getLogger(ComponentConfiguration.class);
    protected String componentName;
    protected boolean configured;
    private String type;
    private boolean notFoundConfigClass;

    protected ComponentConfiguration(String componentName) {
        this.componentName = componentName;
        this.type = null;
        configured = false;
    }

    public boolean isNotFoundConfigClass() {
        return notFoundConfigClass;
    }

    public void setNotFoundConfigClass() {
        this.notFoundConfigClass = true;
    }

    public void configure(Context context) throws ConfigurationException {
        failIfConfigured();
        String confType = context.getString(BasicConfigurationConstants.CONFIG_TYPE);
        if (confType != null && !confType.isEmpty()) {
            this.type = confType;
        }
        // Type can be set by child class constructors, so check if it was.
        if (this.type == null || this.type.isEmpty()) {
            LOGGER.error(ATTRS_MISSING.getError());
            throw new ConfigurationException("Component has no type. Cannot configure. " + componentName);
        }
    }

    protected void failIfConfigured() throws ConfigurationException {
        if (configured) {
            throw new ConfigurationException("Already configured component." + componentName);
        }
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }

    @Override
    public String toString() {
        return toString(0);
    }

    public String toString(int indentCount) {
        StringBuilder indentSb = new StringBuilder();

        for (int i = 0; i < indentCount; i++) {
            indentSb.append(AgentEngineConfiguration.INDENTSTEP);
        }

        String indent = indentSb.toString();

        return indent + "ComponentConfiguration[" + componentName + "]" +
                AgentEngineConfiguration.NEWLINE + indent + AgentEngineConfiguration.INDENTSTEP + "CONFIG: " +
                AgentEngineConfiguration.NEWLINE + indent + AgentEngineConfiguration.INDENTSTEP;
    }

    public String getComponentName() {
        return componentName;
    }

    protected void setConfigured() {
        configured = true;
    }

    public enum ComponentType {
        OTHER(null), REGISTER("Register");

        private final String componentType;

        private ComponentType(String type) {
            componentType = type;
        }

        public String getComponentType() {
            return componentType;
        }
    }
}
