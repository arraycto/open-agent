package com.opencloud.agent.client.handler;

import com.opencloud.agent.client.AbstractClientHandler;
import com.opencloud.agent.client.connect.ConnectionManager;
import com.opencloud.agent.client.executor.ExecutorRequestCenter;
import com.opencloud.agent.codec.RpcResponse;
import com.opencloud.agent.protocol.RpcProtocol;
import com.opencloud.agent.codec.Beat;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.timeout.IdleStateEvent;

import java.net.SocketAddress;
import java.util.concurrent.ThreadPoolExecutor;

public class RpcClientHandler extends AbstractClientHandler {

    private SocketAddress remotePeer;
    private RpcProtocol rpcProtocol;
    private ExecutorRequestCenter executorCenter;

    public RpcClientHandler(ThreadPoolExecutor threadPoolExecutor, Integer retryTime) {
        executorCenter = new ExecutorRequestCenter(threadPoolExecutor, retryTime);
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        this.remotePeer = this.channel.remoteAddress();
    }

    @Override
    public void channelRegistered(ChannelHandlerContext ctx) throws Exception {
        super.channelRegistered(ctx);
        this.channel = ctx.channel();
    }

    @Override
    public void channelRead0(ChannelHandlerContext ctx, Object msg) {
        if (msg instanceof RpcResponse) {
            RpcResponse response = (RpcResponse) msg;
            String requestId = response.getRequestId();
            Object result = response.getResult();
            logger.debug("Receive data: {}", result);
            // receive cmd shell handler
            executorCenter.executorCenter(result);
            // request handler
            RpcFuture rpcFuture = pendingRpc.get(requestId);
            if (rpcFuture != null) {
                pendingRpc.remove(requestId);
                rpcFuture.done(response);
            }
        }
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.error("Client caught exception: " + cause.getMessage());
        ctx.close();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        if (evt instanceof IdleStateEvent) {
            //Send ping
            sendRequest(Beat.BEAT_PING);
            logger.debug("Client send beat-ping to " + remotePeer);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    public void setRpcProtocol(RpcProtocol rpcProtocol) {
        this.rpcProtocol = rpcProtocol;
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        super.channelInactive(ctx);
        ConnectionManager.getInstance().removeHandler(rpcProtocol);
        // 服务端Down掉后，需要释放客户端RpcFuture.get()的Latch锁
        for (RpcFuture rpcFuture : pendingRpc.values()) {
            rpcFuture.done(RpcResponse.fail());
        }
    }
}
