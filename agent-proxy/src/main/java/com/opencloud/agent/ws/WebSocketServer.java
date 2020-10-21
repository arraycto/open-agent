package com.opencloud.agent.ws;

import com.alibaba.fastjson.JSON;
import com.opencloud.agent.ExecutorStatus;
import com.opencloud.agent.codec.SocketInfo;
import com.opencloud.agent.executor.ExecutorCenter;
import com.opencloud.agent.executor.ExecutorFromBrower;
import com.opencloud.agent.executor.ExecutorFromProxy;
import com.opencloud.agent.executor.ExecutorResult;
import com.opencloud.agent.lifecycle.LifecycleState;
import com.opencloud.agent.message.MessageInfo;
import com.opencloud.agent.redis.RedisUtils;
import com.opencloud.agent.sink.ParserRunner;
import com.opencloud.agent.sink.PollableParserRunner;
import com.opencloud.agent.sink.SourceMessage;
import com.opencloud.agent.util.IpUtils;
import com.opencloud.agent.ws.handler.MessageHandlerFactory;
import com.opencloud.config.MessageType;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import javax.websocket.*;
import javax.websocket.server.PathParam;
import javax.websocket.server.ServerEndpoint;
import java.io.IOException;
import java.util.concurrent.atomic.AtomicInteger;

@ServerEndpoint("/websocket/{cid}/{type}")
@Component
public class WebSocketServer {
    /**
     * concurrent包的线程安全Set，用来存放每个客户端对应的MyWebSocket对象。
     */
    private static Logger log = LoggerFactory.getLogger(WebSocketServer.class);

    /**
     * 静态变量，用来记录当前在线连接数。
     */
    private static AtomicInteger atomic = new AtomicInteger(0);
    private static RedisUtils redisUtils;
    private final ParserRunner parserRunner;
    private WebSocketInfo webSocketInfo = new WebSocketInfo();

    public WebSocketServer() {
        parserRunner = new PollableParserRunner();
    }

    @Autowired
    public void setRedisUtils(RedisUtils redisUtils) {
        WebSocketServer.redisUtils = redisUtils;
    }

    public void sendExecutorInfo(String clientId, ExecutorFromBrower info, MessageType messageType) {
        SocketInfo socketInfo = redisUtils.get(clientId, SocketInfo.class);
        Boolean isExecutorId = info.getIsExecutor();
        if (socketInfo == null) {
            // 更新状态
            ExecutorResult executorResult = new ExecutorResult();
            executorResult.setType(MessageType.MESSAGE.getType());
            executorResult.setExecuteStatus(ExecutorStatus.STATUS_NO_CLIENT);
            executorResult.setResult(ExecutorStatus.STATUS_NO_CLIENT.getMessage());
            executorResult.setClientId(clientId);
            executorResult.setExecuteTime(System.currentTimeMillis());
            executorResult.setExecutor(isExecutorId);
            try {
                sendExecutorMessage(executorResult);
            } catch (Exception ex) {
                log.error(ex.getMessage());
            }
            return;
        }
        ExecutorFromProxy executorFromProxy = new ExecutorFromProxy();
        executorFromProxy.setType(messageType.getType());
        executorFromProxy.setCommand(info.getCommand());
        executorFromProxy.setExecuteStatus(info.getStatus());
        executorFromProxy.setExecuteId(info.getExecuteId());
        executorFromProxy.setClientId(clientId);
        executorFromProxy.setExecuteTime(System.currentTimeMillis());
        executorFromProxy.setProxyIp(IpUtils.getLocalIp());
        executorFromProxy.setAgentChannelId(socketInfo.getChannelId());
        executorFromProxy.setExecutor(isExecutorId);
        executorFromProxy.setBrowerIp(info.getBrowerIp());

        SourceMessage sourceMessage = new SourceMessage();
        sourceMessage.setChannelHost(socketInfo.getServerIp());
        sourceMessage.setChannelPort(socketInfo.getServerPort());
        sourceMessage.setExecutorFromProxy(executorFromProxy);
        if (!parserRunner.getLifecycleState().equals(LifecycleState.START)) {
            parserRunner.start();
        }
        parserRunner.put(sourceMessage);
    }

    public int getOnlineCount() {
        return atomic.get();
    }

    public void addOnlineCount() {
        atomic.incrementAndGet();
    }

    public void subOnlineCount() {
        atomic.decrementAndGet();
    }

    public WebSocketInfo getWebSocketInfo() {
        return webSocketInfo;
    }

    /**
     * 连接建立成功调用的方法
     */
    @OnOpen
    public void onOpen(Session session, @PathParam("cid") String cid) {
        int messageType = MessageType.PROXY.getType();
        webSocketInfo.setSession(session);
        webSocketInfo.setClientIp(cid);
        webSocketInfo.setMessageType(messageType);
        if (messageType == MessageType.PROXY.getType()) {
            try {
                this.sendConnectMessage(MessageInfo.getInstance(MessageType.SESSIONID, session.getId()));
            } catch (IOException e) {
                e.printStackTrace();
            }
        } else {
            return;
        }
        //加入set中
        ExecutorCenter.setWebsocket(session.getId(), this);
        addOnlineCount();
        log.info("Welcome clientId:" + cid + ",sessionId:" + session.getId() + ",current is:" + getOnlineCount());
    }

    /**
     * 连接关闭调用的方法
     */
    @OnClose
    public void onClose() {
        //从set中删除
        ExecutorCenter.removeWebsocket(this.webSocketInfo.getSession().getId());
        //在线数减1
        subOnlineCount();
        log.info("连接关闭" + this.webSocketInfo.getClientIp() + ", 当前在线人数为:" + getOnlineCount());
        this.webSocketInfo.setSession(null);
        this.webSocketInfo = null;
        parserRunner.stop();
    }

    /**
     * 收到客户端消息后调用的方法 * * @param message 客户端发送过来的消息
     */
    @OnMessage
    public void onMessage(String message, Session session) {
        log.debug("收到来自窗口" + this.webSocketInfo.getClientIp() + "的信息:" + message);
        try {
            ExecutorResult executorResult = JSON.parseObject(message, ExecutorResult.class);
            IMessageHandler messageHandler = MessageHandlerFactory.getMessageHandler(executorResult.getType());
            if (messageHandler != null) {
                messageHandler.execute(executorResult, session);
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * * @param session * @param error
     */
    @OnError
    public void onError(Session session, Throwable error) {
        log.error("发生错误:" + this.webSocketInfo.getClientIp());
    }

    /**
     * 实现服务器主动推送
     */
    public void sendConnectMessage(MessageInfo info) throws IOException {
        this.webSocketInfo.getSession().getBasicRemote().sendText(info.toString());
    }

    public void sendExecutorMessage(ExecutorResult executorResult) throws Exception {
        try {
            this.webSocketInfo.getSession().getBasicRemote().sendText(executorResult.toString());
        } catch (IOException e) {
            Thread.sleep(5000);
            this.webSocketInfo.getSession().getBasicRemote().sendText(executorResult.toString());
        }
    }

}
