package com.opencloud.agent.client;

import com.opencloud.agent.AgentServer;
import com.opencloud.agent.client.handler.RpcFuture;
import com.opencloud.agent.client.proxy.ProxyReflectFactory;
import com.opencloud.agent.client.proxy.RpcService;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.service.ConnectService;
import com.opencloud.config.Configurable;
import com.opencloud.config.Context;
import com.opencloud.agent.client.discovery.ProxyChannelDiscovery;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collections;
import java.util.concurrent.TimeUnit;

import static com.opencloud.agent.BasicConfigurationConstants.*;
import static com.opencloud.config.MessageType.PROXY;

/**
 * Proxy Client（Create Proxy proxy）
 */
public class ProxyClient extends AgentServer implements Configurable {
    private static final Logger logger = LoggerFactory.getLogger(ProxyClient.class);
    private RpcProtocol rpcProtocol;
    private ProxyChannelDiscovery proxyChannelDiscovery;
    private Integer retryTime = 1;
    private String remoteChannelId;

    public ProxyClient() {
        super();
    }


    @Override
    public void start() {
        super.start();
        this.proxyChannelDiscovery = new ProxyChannelDiscovery(rpcProtocol, retryTime);
        connect();
    }

    private void connect() {
        RpcService connectService = ProxyReflectFactory.createAsyncService(rpcProtocol, ConnectService.class, VERSION_1);
        try {
            RpcFuture rpcFuture = connectService.call(ConnectService.CONNECT_NAME, PROXY.getType());
            this.remoteChannelId = (String) rpcFuture.get(5, TimeUnit.SECONDS);
        } catch (Exception e) {
            logger.error("ProxyClient get remoteChannelId error, remote peer = {},{}", rpcProtocol, e.getMessage());
        }
    }

    @Override
    public void stop() {
        super.stop();
        proxyChannelDiscovery.stop();
    }

    @Override
    public void configure(Context context) {
        synchronized (this) {
            if (context == null) {
                logger.error("Hessc nettyServer config is null, will close!");
                return;
            }
            try {
                rpcProtocol = new RpcProtocol();
                String host = context.getString(CONFIG_SERVER_HOST);
                Integer port = context.getInteger(CONFIG_SERVER_PORT);
                rpcProtocol.setHost(host);
                rpcProtocol.setPort(port);
                rpcProtocol.setServiceInfoList(Collections.emptyList());
                this.retryTime = context.getInteger(CLIENT_RETRY_TIME, 1);
            } catch (Exception e) {
                logger.error("Configure Hessc client error: {}!", e.getMessage());
            }
        }
    }

    public String getRemoteChannelId() {
        return remoteChannelId;
    }

    public RpcProtocol getRpcProtocol() {
        return rpcProtocol;
    }
}

