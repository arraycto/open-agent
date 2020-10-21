package com.opencloud.agent.client.connect;

import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.client.AbstractClientHandler;
import com.opencloud.agent.client.route.RpcLoadBalance;
import com.opencloud.agent.client.route.impl.RpcLoadBalanceRoundRobin;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.CopyOnWriteArraySet;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

/**
 * RPC Connection Manager
 */
public class ConnectionManager {
    private static final Logger logger = LoggerFactory.getLogger(ConnectionManager.class);

    private Map<RpcProtocol, AbstractClientHandler> connectedServerNodes = new ConcurrentHashMap<>();
    private CopyOnWriteArraySet<RpcProtocol> rpcProtocolSet = new CopyOnWriteArraySet<>();
    private ReentrantLock lock = new ReentrantLock();
    private Condition connected = lock.newCondition();
    private RpcLoadBalance loadBalance = new RpcLoadBalanceRoundRobin();
    private volatile boolean isRunning = true;

    private ConnectionManager() {
    }

    public static ConnectionManager getInstance() {
        return SingletonHolder.INSTANCE;
    }

    public Map<RpcProtocol, AbstractClientHandler> getConnectedServerNodes() {
        return connectedServerNodes;
    }

    public CopyOnWriteArraySet<RpcProtocol> getRpcProtocolSet() {
        return rpcProtocolSet;
    }

    public void signalAvailableHandler() {
        lock.lock();
        try {
            connected.signalAll();
        } finally {
            lock.unlock();
        }
    }

    private boolean waitingForHandler() throws InterruptedException {
        lock.lock();
        try {
            logger.warn("Waiting for available service");
            long waitTimeout = 5000;
            return connected.await(waitTimeout, TimeUnit.MILLISECONDS);
        } finally {
            lock.unlock();
        }
    }

    /**
     * 负载策略，适用于客户端client agent服务
     */
    public AbstractClientHandler chooseHandler(boolean waitClientHandler, String serviceKey) throws Exception {
        if (waitClientHandler) {
            checkProtocol();
        }
        RpcProtocol rpcProtocol = loadBalance.route(serviceKey, connectedServerNodes);
        AbstractClientHandler handler = connectedServerNodes.get(rpcProtocol);
        if (handler != null) {
            return handler;
        } else {
            throw new Exception("Can not get available connection");
        }
    }

    /**
     * 定向执行，适用于代理Proxy服务
     */
    public AbstractClientHandler chooseHandler(boolean waitClientHandler, RpcProtocol rpcProtocol) throws Exception {
        // 定向执行
        if (waitClientHandler) {
            checkProtocol();
        }
        AbstractClientHandler handler = connectedServerNodes.get(rpcProtocol);
        if (handler != null) {
            return handler;
        } else {
            throw new Exception("Can not get available connection");
        }
    }

    private void checkProtocol() {
        int size = connectedServerNodes.values().size();
        while (isRunning && size <= 0) {
            try {
                waitingForHandler();
                size = connectedServerNodes.values().size();
            } catch (InterruptedException e) {
                logger.error("Waiting for available service is interrupted!", e);
            }
        }
    }

    public void removeHandler(RpcProtocol rpcProtocol) {
        rpcProtocolSet.remove(rpcProtocol);
        connectedServerNodes.remove(rpcProtocol);
        logger.info("Remove one connection, host: {}, port: {}", rpcProtocol.getHost(), rpcProtocol.getPort());
    }

    public void stop() {
        isRunning = false;
        for (RpcProtocol rpcProtocol : rpcProtocolSet) {
            AbstractClientHandler handler = connectedServerNodes.get(rpcProtocol);
            if (handler != null) {
                handler.close();
            }
            connectedServerNodes.remove(rpcProtocol);
            rpcProtocolSet.remove(rpcProtocol);
        }
        signalAvailableHandler();
    }

    private static class SingletonHolder {
        private static final ConnectionManager INSTANCE = new ConnectionManager();
    }
}
