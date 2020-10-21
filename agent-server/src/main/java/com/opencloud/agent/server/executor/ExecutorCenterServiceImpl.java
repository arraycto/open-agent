package com.opencloud.agent.server.executor;

import cn.hutool.core.util.StrUtil;
import com.opencloud.agent.ExecutorStatus;
import com.opencloud.agent.annotation.NettyRpcService;
import com.opencloud.agent.codec.ExecRequest;
import com.opencloud.agent.codec.RpcResponse;
import com.opencloud.agent.codec.ExecResponse;
import com.opencloud.agent.executor.ExecutorFromProxy;
import com.opencloud.agent.executor.ExecutorFromServer;
import com.opencloud.agent.executor.ExecutorResult;
import com.opencloud.agent.server.context.ApplicationServerContext;
import com.opencloud.agent.service.ExecutorCenterService;
import com.opencloud.config.MessageType;
import io.netty.channel.socket.SocketChannel;
import lombok.extern.slf4j.Slf4j;

import static com.opencloud.agent.BasicConfigurationConstants.VERSION_1;

@Slf4j
@NettyRpcService(value = ExecutorCenterService.class, version = VERSION_1)
public class ExecutorCenterServiceImpl implements ExecutorCenterService {

    public ExecutorCenterServiceImpl() {

    }

    @Override
    public ExecutorResult request(ExecutorFromProxy executorFromProxy) {
        log.info("===>> Request channel cmd:{}", executorFromProxy.toString());
        String agentChannelId = executorFromProxy.getAgentChannelId();
        String proxyChannelId = executorFromProxy.getProxyChannelId();
        String command = executorFromProxy.getCommand();
        String proxyIp = executorFromProxy.getProxyIp();
        long executeId = executorFromProxy.getExecuteId();
        String clientId = executorFromProxy.getClientId();
        boolean isExecutor = executorFromProxy.isExecutor();
        String browerIp = executorFromProxy.getBrowerIp();

        ExecutorResult begin = new ExecutorResult();
        begin.setType(MessageType.MESSAGE.getType());
        begin.setExecuteId(executeId);
        begin.setExecuteStatus(ExecutorStatus.STATUS_SEND_CHANNEL);
        begin.setResult(ExecutorStatus.STATUS_SEND_CHANNEL.getMessage());
        begin.setAgentChannelId(agentChannelId);
        begin.setProxyChannelId(proxyChannelId);
        begin.setProxyIp(proxyIp);
        begin.setCommand(command);
        begin.setExecuteTime(System.currentTimeMillis());
        begin.setClientId(clientId);
        begin.setExecutor(isExecutor);
        begin.setBrowerIp(browerIp);

        if (StrUtil.isBlank(agentChannelId)) {
            begin.setExecuteStatus(ExecutorStatus.STATUS_SEND_PROXY_ERROR);
            begin.setResult(ExecutorStatus.STATUS_SEND_PROXY_ERROR.getMessage());
            log.error("Request ExecutorFromProxy channelId is null :{}", executorFromProxy.toString());
            return begin;
        }

        SocketChannel ctx = ApplicationServerContext.getSocketChannelByChannelId(agentChannelId);
        if (ctx == null) {
            begin.setExecuteStatus(ExecutorStatus.STATUS_SEND_PROXY_ERROR);
            begin.setResult(ExecutorStatus.STATUS_SEND_PROXY_ERROR.getMessage());
            return begin;
        }

        ExecutorFromServer executorFromServer = new ExecutorFromServer();
        executorFromServer.setCommand(command);
        executorFromServer.setExecuteId(executeId);
        executorFromServer.setType(MessageType.COMMAND.getType());
        executorFromServer.setProxyIp(proxyIp);
        executorFromServer.setAgentChannelId(agentChannelId);
        executorFromServer.setProxyChannelId(proxyChannelId);
        executorFromServer.setClientId(clientId);
        executorFromServer.setExecuteTime(System.currentTimeMillis());
        executorFromServer.setExecutor(isExecutor);
        executorFromServer.setBrowerIp(browerIp);
        // 创建客户端Agent执行返回的条件 ID=CMD_REQUEST_ID
        RpcResponse response = new RpcResponse();
        response.setRequestId(ExecRequest.CMD_REQUEST_ID);
        response.setResult(executorFromServer);
        ctx.writeAndFlush(response);
        return begin;
    }

    /**
     * client agent response返回结果
     */
    @Override
    public void response(ExecutorResult executorResult) {
        log.info("<<=== Response channel: {}", executorResult.toString());
        String proxyChannelId = executorResult.getProxyChannelId();
        SocketChannel ctx = ApplicationServerContext.getSocketChannelByChannelId(proxyChannelId);
        if (ctx == null) {
            log.error("Response SocketChannel is null :{}", executorResult.toString());
            return;
        }
        RpcResponse response = new RpcResponse();
        response.setRequestId(ExecResponse.CMD_RESPONSE_ID);
        response.setResult(executorResult);
        ctx.writeAndFlush(response);
    }
}
