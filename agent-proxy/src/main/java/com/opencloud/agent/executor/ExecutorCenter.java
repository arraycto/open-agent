package com.opencloud.agent.executor;

import cn.hutool.core.util.CharUtil;
import com.opencloud.agent.redis.RedisUtils;
import com.opencloud.agent.util.IpUtils;
import com.opencloud.agent.ws.WebSocketServer;
import com.opencloud.config.MessageType;
import lombok.extern.slf4j.Slf4j;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.concurrent.ConcurrentHashMap;

import static com.opencloud.agent.BasicConfigurationConstants.EXECUTOR_RESULT;
import static com.opencloud.agent.ExecutorStatus.STATUS_SEND_CHANNEL_ERROR;

@Slf4j
@Component
public class ExecutorCenter {


    private static RedisUtils redisUtils;
    /**
     * 浏览器websocket链接信息
     */
    private static ConcurrentHashMap<String, WebSocketServer> WEBSOCKET_INFOS = new ConcurrentHashMap<>();
    /**
     * 所有执行器信息
     * <p>
     * key = executeId
     */
    private static ConcurrentHashMap<Long, ExecutorFromBrower> ALL_EXECUTOR = new ConcurrentHashMap<>();

    public static WebSocketServer getWebsocket(String sessionId) {
        return WEBSOCKET_INFOS.get(sessionId);
    }

    public static void setWebsocket(String sessionId, WebSocketServer webSocketServer) {
        WEBSOCKET_INFOS.put(sessionId, webSocketServer);
    }

    public static void removeWebsocket(String sessionId) {
        WEBSOCKET_INFOS.remove(sessionId);
    }

    public static ExecutorFromBrower getExecutor(Long executeId) {
        return ALL_EXECUTOR.get(executeId);
    }

    public static void setExecutor(Long executeId, ExecutorFromBrower executorFromBrower) {
        log.info("======>>> {}", executorFromBrower.toString());
        ALL_EXECUTOR.put(executeId, executorFromBrower);
    }

    public static void executor(WebSocketServer socketServer, ExecutorFromBrower executorFromBrower) {
        String clientId = executorFromBrower.getClientId();
        try {
            socketServer.sendExecutorInfo(clientId, executorFromBrower, MessageType.COMMAND);
        } catch (Exception e) {
            log.error("代理ProxyService执行异常{}", e.getMessage(), e);
            doExceptionResult(socketServer, clientId, e, executorFromBrower);
        }
    }

    /**
     * 异常数据处理
     */
    private static void doExceptionResult(WebSocketServer socketServer, String clientId, Exception e,
                                          ExecutorFromBrower executorFromBrower) {
        ExecutorResult executorResult = new ExecutorResult();
        executorResult.setType(MessageType.MESSAGE.getType());
        executorResult.setExecuteStatus(STATUS_SEND_CHANNEL_ERROR);
        executorResult.setResult(STATUS_SEND_CHANNEL_ERROR.getMessage() + CharUtil.COLON + e.getMessage());
        executorResult.setClientId(clientId);
        executorResult.setExecuteId(executorFromBrower.getExecuteId());
        executorResult.setAgentChannelId(executorFromBrower.getClientId());
        executorResult.setProxyIp(IpUtils.getLocalIp());
        executorResult.setCommand(executorFromBrower.getCommand());
        executorResult.setExecutor(false);
        executorResult.setExecuteTime(System.currentTimeMillis());

        try {
            socketServer.sendExecutorMessage(executorResult);
        } catch (Exception ex) {
            log.error("ExecutorCenter doExceptionResult error:{}", ex.getMessage());
        }
        redisUtils.push(EXECUTOR_RESULT, executorResult.toString());
    }

    /**
     * 返回数据处理
     */
    public static void doResponseResult(ExecutorResult executorResult) {
        // 数据库/Redis保存数据
        Long executeId = executorResult.getExecuteId();
        redisUtils.push(EXECUTOR_RESULT, executorResult.toString());
        // socket 传输数据
        ExecutorFromBrower executorFromBrower = getExecutor(executeId);
        if (executorFromBrower == null) {
            return;
        }
        String sessionId = executorFromBrower.getSessionId();
        WebSocketServer socketServer = getWebsocket(sessionId);
        if (socketServer == null) {
            return;
        }
        log.debug("<<<======{}", executorResult.toString());
        try {
            socketServer.sendExecutorMessage(executorResult);
        } catch (Exception ex) {
            log.error("DoExecuteResult sendExecutorMessage error:{}", executorResult, ex);
        }
    }

    /**
     * 发送数据处理
     */
    public static void doSendExecuteResult(ExecutorFromProxy executorFromProxy) {
        Long executeId = executorFromProxy.getExecuteId();
        String command = executorFromProxy.getCommand();
        // result
        ExecutorResult executorResult = new ExecutorResult();
        executorResult.setExecuteStatus(executorFromProxy.getExecuteStatus());
        executorResult.setResult(executorFromProxy.getExecuteStatus().getMessage());
        executorResult.setCommand(command);
        executorResult.setClientId(executorFromProxy.getClientId());
        executorResult.setType(MessageType.MESSAGE.getType());
        executorResult.setExecuteId(executeId);
        executorResult.setExecuteTime(System.currentTimeMillis());
        executorResult.setAgentChannelId(executorFromProxy.getClientId());
        executorResult.setProxyIp(IpUtils.getLocalIp());
        executorResult.setExecutor(false);
        redisUtils.push(EXECUTOR_RESULT, executorResult.toString());
        // socket
        try {
            ExecutorFromBrower executorFromBrower = getExecutor(executeId);
            if (executorFromBrower == null) {
                return;
            }
            String sessionId = executorFromBrower.getSessionId();
            WebSocketServer socketServer = getWebsocket(sessionId);
            if (socketServer == null) {
                log.error("WebSocketServer=null,{}", executorFromProxy.toString());
                return;
            }

            socketServer.sendExecutorMessage(executorResult);
        } catch (Exception ex) {
            log.error("SoSendExecuteResult error:{}", executorResult.toString(), ex);
        }
    }

    @Autowired
    public void setRedisUtils(RedisUtils rt) {
        redisUtils = rt;
    }
}
