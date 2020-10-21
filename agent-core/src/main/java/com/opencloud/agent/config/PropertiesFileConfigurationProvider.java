package com.opencloud.agent.config;

import com.opencloud.config.AgentEngineConfiguration;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.BufferedReader;
import java.io.File;
import java.io.FileReader;
import java.io.IOException;
import java.util.HashMap;
import java.util.Properties;

public class PropertiesFileConfigurationProvider extends AbstractConfigurationProvider {

    private static final Logger LOGGER = LoggerFactory.getLogger(PropertiesFileConfigurationProvider.class);
    private static final String DEFAULT_PROPERTIES_IMPLEMENTATION = "java.util.Properties";

    private final File file;

    public PropertiesFileConfigurationProvider(File file) {
        super();
        this.file = file;
    }

    @Override
    public AgentEngineConfiguration getAgentEngineConfiguration() {
        BufferedReader reader = null;
        try {
            reader = new BufferedReader(new FileReader(file));
            String resolverClassName = System.getProperty("propertiesImplementation", DEFAULT_PROPERTIES_IMPLEMENTATION);
            Class<? extends Properties> propsclass = Class.forName(resolverClassName).asSubclass(Properties.class);
            Properties properties = propsclass.newInstance();
            properties.load(reader);
            return new AgentEngineConfiguration(toMap(properties));
        } catch (IOException ex) {
            LOGGER.error("Unable to load file:" + file + " (I/O failure) - Exception follows.", ex);
        } catch (ClassNotFoundException e) {
            LOGGER.error("Configuration resolver class not found", e);
        } catch (InstantiationException e) {
            LOGGER.error("Instantiation exception", e);
        } catch (IllegalAccessException e) {
            LOGGER.error("Illegal access exception", e);
        } finally {
            if (reader != null) {
                try {
                    reader.close();
                } catch (IOException ex) {
                    LOGGER.warn("Unable to close file reader for file: " + file, ex);
                }
            }
        }
        return new AgentEngineConfiguration(new HashMap<>());
    }
}
