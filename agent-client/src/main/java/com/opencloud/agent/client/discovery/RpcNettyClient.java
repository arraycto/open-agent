package com.opencloud.agent.client.discovery;

import com.opencloud.agent.BasicConfigurationConstants;
import com.opencloud.agent.client.AbstractClientHandler;
import com.opencloud.agent.client.handler.RpcClientHandler;
import com.opencloud.agent.client.handler.RpcClientInitializer;
import com.opencloud.agent.client.proxy.ProxyReflectFactory;
import com.opencloud.agent.client.proxy.RpcService;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.client.AbstractClient;
import com.opencloud.agent.protocol.RpcServiceInfo;
import com.opencloud.agent.service.ConnectService;
import com.opencloud.agent.util.ThreadPoolUtil;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;

import java.net.InetSocketAddress;
import java.util.HashSet;
import java.util.List;
import java.util.Optional;

import static com.opencloud.config.MessageType.CLIENT;

public class RpcNettyClient extends AbstractClient {


    public RpcNettyClient() {
        this.eventLoopGroup = new NioEventLoopGroup(4);
        this.threadPoolExecutor = ThreadPoolUtil.makeServerThreadPool(
                RpcNettyClient.class.getSimpleName(), 16, 16);
    }


    @Override
    public void updateConnectedServer(List<RpcProtocol> serviceList, Integer retryTime) {
        // Now using 2 collections to manage the service info and TCP connections because making the connection is async
        // Once service info is updated on ZK, will trigger this function
        // Actually client should only care about the service it is using
        if (serviceList != null && serviceList.size() > 0) {
            // Update local server nodes cache
            HashSet<RpcProtocol> serviceSet = new HashSet<>(serviceList.size());
            serviceSet.addAll(serviceList);

            /*
             * channel会在每个IDC中部署一套集群，Agent会随机在其中的一台建立长连接。上面就是中心，
             * 中心做了双机房容灾部署，同时在线提供服务，其中一个机房挂掉对业务是没有影响的。
             */
            Integer mode = Optional.of(chooseProtocol(serviceList.size())).get();
            RpcProtocol protocol = serviceList.get(mode);
            connectServerNode(protocol, retryTime);

            // Close and remove invalid server nodes
            for (RpcProtocol rpcProtocol : connectionManager.getRpcProtocolSet()) {
                if (!serviceSet.contains(rpcProtocol)) {
                    logger.info("Remove invalid service: " + rpcProtocol.toJson());
                    AbstractClientHandler handler = connectionManager.getConnectedServerNodes().get(rpcProtocol);
                    if (handler != null) {
                        handler.close();
                    }
                    connectionManager.getConnectedServerNodes().remove(rpcProtocol);
                    connectionManager.getRpcProtocolSet().remove(rpcProtocol);
                }
            }
        } else {
            // No available service
            logger.error("No available service!");
            for (RpcProtocol rpcProtocol : connectionManager.getRpcProtocolSet()) {
                AbstractClientHandler handler = connectionManager.getConnectedServerNodes().get(rpcProtocol);
                if (handler != null) {
                    handler.close();
                }
                connectionManager.getConnectedServerNodes().remove(rpcProtocol);
                connectionManager.getRpcProtocolSet().remove(rpcProtocol);
            }
        }
    }

    @Override
    protected Channel connectServerNode(RpcProtocol rpcProtocol, Integer retryTime) {
        if (rpcProtocol.getServiceInfoList() == null || rpcProtocol.getServiceInfoList().isEmpty()) {
            logger.info("No service protocol on node, host: {}, port: {}", rpcProtocol.getHost(), rpcProtocol.getPort());
            return null;
        }
        connectionManager.getRpcProtocolSet().add(rpcProtocol);
        logger.info("New service protocol node, host: {}, port: {}", rpcProtocol.getHost(), rpcProtocol.getPort());
        for (RpcServiceInfo serviceProtocol : rpcProtocol.getServiceInfoList()) {
            logger.info("New service protocol info, name: {}, version: {}", serviceProtocol.getServiceName(), serviceProtocol.getVersion());
        }
        final InetSocketAddress remotePeer = new InetSocketAddress(rpcProtocol.getHost(), rpcProtocol.getPort());
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new RpcClientInitializer(threadPoolExecutor, retryTime));

        ChannelFuture channelFuture = bootstrap.connect(remotePeer);
        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            if (channelFuture1.isSuccess()) {
                Channel channel = channelFuture1.channel();
                RpcClientHandler handler = channel.pipeline().get(RpcClientHandler.class);
                connectionManager.getConnectedServerNodes().put(rpcProtocol, handler);
                handler.setRpcProtocol(rpcProtocol);
                logger.info("Starting connect to remote server, remote peer = {}........", remotePeer);
            } else {
                logger.error("Can not connect to remote server, remote peer = {}", remotePeer);
            }
            connectionManager.signalAvailableHandler();
        });
        // 异步执行，避免zookeeper无法更新
        RpcService connectService = ProxyReflectFactory.createAsyncService(ConnectService.class, BasicConfigurationConstants.VERSION_1);
        try {
            connectService.call(ConnectService.CONNECT_NAME, CLIENT.getType());
        } catch (Exception e) {
            logger.error("Can not connect to remote server, remote peer = {},{}", remotePeer, e);
        }
        logger.info("Successfully connect to remote server, remote peer = {}", remotePeer);
        return channelFuture.channel();
    }

}
