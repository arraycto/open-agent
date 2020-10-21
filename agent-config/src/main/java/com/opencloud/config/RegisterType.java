package com.opencloud.config;


import static com.opencloud.agent.BasicConfigurationConstants.ENGINE_CONFIG_CLIENT_STORE_PATH;
import static com.opencloud.agent.BasicConfigurationConstants.ENGINE_CONFIG_SERVER_STORE_PATH;

public enum RegisterType implements ComponentWithClassName {

    OTHER(null, ENGINE_CONFIG_CLIENT_STORE_PATH),

    SERVER("com.opencloud.agent.server.RpcServer", ENGINE_CONFIG_SERVER_STORE_PATH),

    CLIENT("com.opencloud.agent.client.RpcClient", ENGINE_CONFIG_CLIENT_STORE_PATH);

    private final String sourceClassName;
    private final String engineConfigStorePath;

    RegisterType(String sourceClassName, String engineConfigStorePath) {
        this.sourceClassName = sourceClassName;
        this.engineConfigStorePath = engineConfigStorePath;
    }

    @Override
    public String getClassName() {
        return sourceClassName;
    }

    @Override
    public String getStoreName() {
        return engineConfigStorePath;
    }
}
