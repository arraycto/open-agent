package com.opencloud.agent.server.handler;

import com.opencloud.agent.codec.RpcResponse;
import com.opencloud.agent.codec.Beat;
import com.opencloud.agent.codec.RpcRequest;
import com.opencloud.agent.server.context.ApplicationServerContext;
import com.opencloud.agent.server.context.UserContextHolder;
import com.opencloud.agent.util.ServiceUtil;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFutureListener;
import io.netty.channel.ChannelHandlerContext;
import io.netty.channel.SimpleChannelInboundHandler;
import io.netty.channel.socket.SocketChannel;
import io.netty.handler.timeout.IdleStateEvent;
import net.sf.cglib.reflect.FastClass;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Map;


/**
 * RPC Handler（RPC request processor）
 */
public class RpcServerHandler extends SimpleChannelInboundHandler<Object> {

    private static final Logger logger = LoggerFactory.getLogger(RpcServerHandler.class);

    private final Map<String, Object> handlerMap;

    public RpcServerHandler(Map<String, Object> handlerMap) {
        this.handlerMap = handlerMap;
    }

    @Override
    public void channelActive(ChannelHandlerContext ctx) throws Exception {
        super.channelActive(ctx);
        Channel channel = ctx.channel();
        ApplicationServerContext.initSocketChannel((SocketChannel) channel);
    }

    @Override
    public void channelRead0(final ChannelHandlerContext ctx, final Object msg) {
        Channel channel = ctx.channel();
        String uuid = channel.id().asLongText();
        UserContextHolder.getInstance().put(UserContextHolder.CHANNEL_ID, uuid);
        logger.debug("request channelRead0 come in: {}", uuid);
        if (msg instanceof RpcRequest) {
            RpcRequest request = (RpcRequest) msg;
            String requestId = request.getRequestId();
            // filter beat ping
            if (Beat.BEAT_ID.equalsIgnoreCase(requestId)) {
                ApplicationServerContext.refresh();
                logger.info("Server read heartbeat ping");
            } else {
                logger.debug("Receive request {}", requestId);
                RpcResponse response = new RpcResponse();
                response.setRequestId(requestId);
                try {
                    Object result = handle(request);
                    response.setResult(result);
                } catch (Throwable t) {
                    response.setError(t.toString());
                    logger.error("RPC Server handle request error", t);
                }
                ctx.writeAndFlush(response).addListener((ChannelFutureListener) channelFuture -> logger.debug("Send response for request " + requestId));
            }
        }
    }

    private Object handle(RpcRequest request) throws Throwable {
        String className = request.getClassName();
        String version = request.getVersion();
        String serviceKey = ServiceUtil.makeServiceKey(className, version);
        Object serviceBean = handlerMap.get(serviceKey);
        if (serviceBean == null) {
            logger.error("Can not find service implement with interface name: {} and version: {}", className, version);
            return null;
        }

        Class<?> serviceClass = serviceBean.getClass();
        String methodName = request.getMethodName();
        Class<?>[] parameterTypes = request.getParameterTypes();
        Object[] parameters = request.getParameters();

        logger.debug(serviceClass.getName());
        logger.debug(methodName);
        for (Class<?> parameterType : parameterTypes) {
            logger.debug(parameterType.getName());
        }
        for (Object parameter : parameters) {
            if (parameter != null) {
                logger.debug(parameter.toString());
            }
        }

        // JDK reflect
//        Method method = serviceClass.getMethod(methodName, parameterTypes);
//        method.setAccessible(true);
//        return method.invoke(serviceBean, parameters);

        // Cglib reflect
        FastClass serviceFastClass = FastClass.create(serviceClass);
        // for higher-performance
        int methodIndex = serviceFastClass.getIndex(methodName, parameterTypes);
        return serviceFastClass.invoke(methodIndex, serviceBean, parameters);
    }

    @Override
    public void exceptionCaught(ChannelHandlerContext ctx, Throwable cause) {
        logger.warn("Server caught exception: " + cause.getMessage());
        ApplicationServerContext.removeSocketChannel(ctx.channel().id().asLongText());
        ctx.close();
        UserContextHolder.getInstance().clear();
    }

    @Override
    public void userEventTriggered(ChannelHandlerContext ctx, Object evt) throws Exception {
        ApplicationServerContext.removeSocketChannel(ctx.channel().id().asLongText());
        UserContextHolder.getInstance().clear();
        if (evt instanceof IdleStateEvent) {
            ctx.channel().close();
            logger.warn("Channel idle in last {} seconds, close it", Beat.BEAT_TIMEOUT);
        } else {
            super.userEventTriggered(ctx, evt);
        }
    }

    @Override
    public void channelInactive(ChannelHandlerContext ctx) throws Exception {
        String channelId = ctx.channel().id().asLongText();
        logger.warn("Channel close, channelId:{}", channelId);
        ApplicationServerContext.removeSocketChannel(channelId);
        UserContextHolder.getInstance().clear();
        super.handlerRemoved(ctx);
    }
}
