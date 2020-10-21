package com.opencloud.agent.client.discovery;

import com.opencloud.agent.client.AbstractClient;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.util.ThreadPoolUtil;
import com.opencloud.agent.client.handler.ProxyClientHandler;
import com.opencloud.agent.client.handler.ProxyClientInitializer;
import io.netty.bootstrap.Bootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioSocketChannel;
import lombok.SneakyThrows;

import java.net.InetSocketAddress;
import java.util.List;

public class ProxyNettyClient extends AbstractClient {

    public ProxyNettyClient() {
        this.eventLoopGroup = new NioEventLoopGroup(4);
        this.threadPoolExecutor = ThreadPoolUtil.makeServerThreadPool(
                ProxyNettyClient.class.getSimpleName(), 16, 16);
    }

    @Override
    public void updateConnectedServer(List<RpcProtocol> serviceList, Integer retryTime) {

    }

    @SneakyThrows
    @Override
    protected Channel connectServerNode(RpcProtocol rpcProtocol, Integer retryTime) {
        connectionManager.getRpcProtocolSet().add(rpcProtocol);
        logger.info("New service protocol node, host: {}, port: {}", rpcProtocol.getHost(), rpcProtocol.getPort());
        final InetSocketAddress remotePeer = new InetSocketAddress(rpcProtocol.getHost(), rpcProtocol.getPort());
        Bootstrap bootstrap = new Bootstrap();
        bootstrap.group(eventLoopGroup)
                .channel(NioSocketChannel.class)
                .handler(new ProxyClientInitializer(threadPoolExecutor, retryTime));

        ChannelFuture channelFuture = bootstrap.connect(remotePeer);
        channelFuture.addListener((ChannelFutureListener) channelFuture1 -> {
            if (channelFuture1.isSuccess()) {
                Channel channel = channelFuture1.channel();
                ProxyClientHandler handler = channel.pipeline().get(ProxyClientHandler.class);
                connectionManager.getConnectedServerNodes().put(rpcProtocol, handler);
                handler.setRpcProtocol(rpcProtocol);
                logger.info("Starting connect to remote server, remote peer = {}........", remotePeer);
            } else {
                logger.error("Can not connect to remote server, remote peer = {}", remotePeer);
            }
            // 此地，必须释放，不像是agent client，可以等待zk唤醒
            connectionManager.signalAvailableHandler();
        });
        logger.info("Successfully connect to remote server, remote peer = {}", remotePeer);
        return channelFuture.channel();
    }

}
