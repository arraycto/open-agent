package com.opencloud.agent.client.discovery;

import com.opencloud.agent.protocol.RpcProtocol;
import lombok.SneakyThrows;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * Proxy服务发现
 */
public class ProxyChannelDiscovery {
    private static final Logger logger = LoggerFactory.getLogger(ProxyChannelDiscovery.class);
    private ProxyNettyClient proxyNettyClient;
    private Integer retryTime;
    private RpcProtocol rpcProtocol;

    public ProxyChannelDiscovery(RpcProtocol rpcProtocol, Integer retryTime) {
        this.proxyNettyClient = new ProxyNettyClient();
        this.retryTime = retryTime;
        this.rpcProtocol = rpcProtocol;
        discoveryService();
    }

    @SneakyThrows
    private void discoveryService() {
        try {
            proxyNettyClient.connectServerNode(rpcProtocol, retryTime);
        } catch (Exception ex) {
            logger.error("Collect channel node exception:{} ", ex.getMessage(), ex);
        }
    }

    public void stop() {
        proxyNettyClient.stop();
    }

}
