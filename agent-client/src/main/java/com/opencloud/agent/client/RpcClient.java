package com.opencloud.agent.client;

import com.opencloud.agent.BasicConfigurationConstants;
import com.opencloud.agent.AgentServer;
import com.opencloud.agent.client.discovery.ZkServiceDiscovery;
import com.opencloud.config.Configurable;
import com.opencloud.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * RPC Client（Create RPC proxy）
 */
public class RpcClient extends AgentServer implements Configurable {
    private static final Logger logger = LoggerFactory.getLogger(RpcClient.class);
    private String registerAddress;
    private ZkServiceDiscovery zkServiceDiscovery;
    private Integer retryTime = 1;
    private String registerScheme;
    private String registerAuth;

    public RpcClient() {
        super();
    }


    @Override
    public void start() {
        super.start();
        this.zkServiceDiscovery = new ZkServiceDiscovery(registerAddress, retryTime, registerScheme, registerAuth);
    }

    @Override
    public void stop() {
        super.stop();
        zkServiceDiscovery.stop();
    }

    @Override
    public void configure(Context context) {
        synchronized (this) {
            if (context == null) {
                logger.error("Hessc nettyServer config is null, will close!");
                return;
            }
            try {
                this.registerAddress = context.getString(BasicConfigurationConstants.CONFIG_REGISTER_ADDRESS);
                this.retryTime = context.getInteger(BasicConfigurationConstants.CLIENT_RETRY_TIME, 1);
                this.registerScheme = context.getString(BasicConfigurationConstants.CONFIG_REGISTER_SCHEME);
                this.registerAuth = context.getString(BasicConfigurationConstants.CONFIG_REGISTER_AUTH);
            } catch (Exception e) {
                logger.error("configure Hessc client error: {}!", e.getMessage());
            }
        }
    }

}

