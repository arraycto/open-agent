package com.opencloud.agent.sink;

import cn.hutool.core.net.NetUtil;
import com.opencloud.agent.client.ProxyClient;
import com.opencloud.agent.client.proxy.ProxyReflectFactory;
import com.opencloud.agent.client.proxy.RpcService;
import com.opencloud.agent.executor.ExecutorCenter;
import com.opencloud.agent.executor.ExecutorFromProxy;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.service.ExecutorCenterService;
import com.opencloud.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.net.InetSocketAddress;
import java.util.List;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;
import java.util.concurrent.LinkedBlockingQueue;

import static com.opencloud.agent.BasicConfigurationConstants.*;
import static com.opencloud.agent.ExecutorStatus.*;
import static com.opencloud.agent.service.ExecutorCenterService.REQUEST;

public class EventParserProcessor extends AbstractParserProcessor {
    private static final Logger logger = LoggerFactory.getLogger(EventParserProcessor.class);
    private final Map<InetSocketAddress, ProxyClient> PROXY_CLIENT_MAP;
    protected LinkedBlockingQueue<SourceMessage> queue;
    private int defaultQueueSize = 100;

    public EventParserProcessor() {
        super();
        queue = new LinkedBlockingQueue<>(defaultQueueSize);
        PROXY_CLIENT_MAP = new ConcurrentHashMap<>();
    }

    @Override
    public void configure(Context context) {
        this.defaultQueueSize = context.getInteger(DEFAULT_PROXY_PROCESSOR_SIZE, 100);
    }

    @Override
    public synchronized void start() {
        super.start();
    }

    @Override
    public synchronized void stop() {
        queue.clear();
        PROXY_CLIENT_MAP.clear();
        super.stop();
    }

    @Override
    public void puts(List<SourceMessage> messages) {
        for (SourceMessage message : messages) {
            put(message);
        }
    }

    @Override
    public void put(SourceMessage message) {
        try {
            this.queue.put(message);
            ExecutorFromProxy executorFromProxy = message.getExecutorFromProxy();
            executorFromProxy.setExecuteStatus(STATUS_SEND_CHANNEL_WAIT);
            ExecutorCenter.doSendExecuteResult(executorFromProxy);
        } catch (InterruptedException e) {
            logger.error("Parser processor command queue max size is {},so process Interrupted", defaultQueueSize);
            ExecutorFromProxy executorFromProxy = message.getExecutorFromProxy();
            executorFromProxy.setExecuteStatus(STATUS_SEND_CHANNEL_WAIT_ERROR);
            ExecutorCenter.doSendExecuteResult(executorFromProxy);
        }
    }

    @Override
    public void removeProxy(InetSocketAddress clientAddress) {
        PROXY_CLIENT_MAP.remove(clientAddress);
    }

    @Override
    public Status process() {
        SourceMessage sourceMessage = null;
        InetSocketAddress inetSocketAddress = null;
        try {
            // 从QUEUE中取数据（阻塞模式，直到取到数据为止）
            sourceMessage = queue.take();
            String channelHost = sourceMessage.getChannelHost();
            Integer channelPort = sourceMessage.getChannelPort();
            inetSocketAddress = NetUtil.buildInetSocketAddress(channelHost, channelPort);
            ProxyClient proxyClient = PROXY_CLIENT_MAP.get(inetSocketAddress);
            if (proxyClient == null) {
                Context context = new Context();
                context.put(CONFIG_SERVER_HOST, channelHost);
                context.put(CONFIG_SERVER_PORT, channelPort + "");
                proxyClient = new ProxyClient();
                proxyClient.configure(context);
                proxyClient.start();
                PROXY_CLIENT_MAP.put(inetSocketAddress, proxyClient);
            }
            RpcProtocol rpcProtocol = proxyClient.getRpcProtocol();
            String remoteChannelId = proxyClient.getRemoteChannelId();
            if (remoteChannelId == null) {
                logger.error("remoteChannelId is null");
                doException(sourceMessage, inetSocketAddress);
                return Status.BACKOFF;
            }
            // send connect info
            ExecutorFromProxy executorFromProxy = sourceMessage.getExecutorFromProxy();
            executorFromProxy.setProxyChannelId(remoteChannelId);
            // 异步执行
            RpcService asyncService = ProxyReflectFactory.createAsyncService(rpcProtocol, ExecutorCenterService.class, VERSION_1);
            asyncService.call(REQUEST, executorFromProxy);
            executorFromProxy.setExecuteStatus(STATUS_SEND_CHANNEL_WAIT_SUCCESS);
            ExecutorCenter.doSendExecuteResult(executorFromProxy);
            // 同步执行
//            ExecutorCenterService service = ProxyReflectFactory.createService(rpcProtocol, ExecutorCenterService.class, VERSION_1);
//            ExecutorResult executorResult = service.request(executorFromProxy);
//            ExecutorCenter.doExecuteResult(executorResult);
        } catch (InterruptedException e) {
            logger.error("Interrupted execute log interrupted {}", e.getMessage());
            doException(sourceMessage, inetSocketAddress);
            return Status.BACKOFF;
        } catch (Throwable e) {
            logger.error("Execute log error: {}", e.getMessage());
            doException(sourceMessage, inetSocketAddress);
            return Status.BACKOFF;
        }
        return Status.READY;
    }

    private void doException(SourceMessage sourceMessage, InetSocketAddress inetSocketAddress) {
        if (sourceMessage != null) {
            ExecutorFromProxy executorFromProxy = sourceMessage.getExecutorFromProxy();
            executorFromProxy.setExecuteStatus(STATUS_SEND_CHANNEL_QUEUE_ERROR);
            ExecutorCenter.doSendExecuteResult(executorFromProxy);
        }

        if (inetSocketAddress != null) {
            PROXY_CLIENT_MAP.remove(inetSocketAddress);
        }
    }

}
