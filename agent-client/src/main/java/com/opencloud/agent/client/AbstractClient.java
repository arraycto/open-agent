package com.opencloud.agent.client;

import com.opencloud.agent.client.connect.ConnectionManager;
import com.opencloud.agent.lifecycle.LifecycleAware;
import com.opencloud.agent.lifecycle.LifecycleState;
import com.opencloud.agent.protocol.RpcProtocol;
import io.netty.channel.Channel;
import io.netty.channel.EventLoopGroup;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.List;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.atomic.AtomicInteger;

public abstract class AbstractClient implements LifecycleAware {

    protected static final Logger logger = LoggerFactory.getLogger(AbstractClient.class);
    protected final AtomicInteger nextIndex = new AtomicInteger();
    protected ThreadPoolExecutor threadPoolExecutor;
    protected ConnectionManager connectionManager = ConnectionManager.getInstance();
    protected EventLoopGroup eventLoopGroup;
    protected LifecycleState lifecycleState;

    @Override
    public void start() {
        this.lifecycleState = LifecycleState.START;
    }

    @Override
    public void stop() {
        this.lifecycleState = LifecycleState.STOP;
        connectionManager.stop();
        eventLoopGroup.shutdownGracefully();
        if (threadPoolExecutor != null) {
            threadPoolExecutor.shutdownNow();
        }
    }

    @Override
    public LifecycleState getLifecycleState() {
        return lifecycleState;
    }

    public int chooseProtocol(int modulo) {
        for (; ; ) {
            int current = nextIndex.get();
            int next = (current + 1) % modulo;
            if (nextIndex.compareAndSet(current, next) && current < modulo) {
                return current;
            }
        }

    }

    public abstract void updateConnectedServer(List<RpcProtocol> serviceList, Integer retryTime);

    protected abstract Channel connectServerNode(RpcProtocol rpcProtocol, Integer retryTime);
}
