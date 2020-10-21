package com.opencloud.agent.server;

import com.opencloud.agent.AgentServer;
import com.opencloud.agent.BasicConfigurationConstants;
import com.opencloud.agent.server.context.ApplicationServerContext;
import com.opencloud.agent.server.registry.ServiceRegistry;
import com.opencloud.agent.util.IpUtils;
import com.opencloud.agent.server.handler.RpcServerInitializer;
import com.opencloud.agent.util.ServiceUtil;
import com.opencloud.config.Configurable;
import com.opencloud.config.Context;
import io.netty.bootstrap.ServerBootstrap;
import io.netty.channel.Channel;
import io.netty.channel.ChannelFuture;
import io.netty.channel.ChannelOption;
import io.netty.channel.EventLoopGroup;
import io.netty.channel.nio.NioEventLoopGroup;
import io.netty.channel.socket.nio.NioServerSocketChannel;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.HashMap;
import java.util.Map;


/**
 * @author xielianjun
 */
public class NettyServer extends AgentServer implements Configurable {
    private static final Logger logger = LoggerFactory.getLogger(NettyServer.class);

    private String host;
    private Integer port;
    private ServiceRegistry serviceRegistry;
    private Map<String, Object> serviceMap = new HashMap<>();
    private Channel serverChannel;
    private NettyServerRunner nettyServerRunner;
    private RedisSource redisSource;

    public NettyServer() {
        super();
        redisSource = new RedisSource();
    }

    public void addService(String interfaceName, String version, Object serviceBean) {
        logger.info("Adding service, interface: {}, version: {}, bean：{}", interfaceName, version, serviceBean);
        String serviceKey = ServiceUtil.makeServiceKey(interfaceName, version);
        serviceMap.put(serviceKey, serviceBean);
    }

    /**
     * 关闭当前server
     */
    public void closeServer() {
        if (serverChannel != null) {
            logger.info("close server");
            serverChannel.close();
            serverChannel = null;
        }
    }

    @Override
    public void start() {
        super.start();
        nettyServerRunner = new NettyServerRunner();
        nettyServerRunner.start();
        redisSource.start();
    }

    @Override
    public void stop() {
        super.stop();
        closeServer();
        // destroy server thread
        nettyServerRunner.interrupt();
        redisSource.stop();
    }


    @Override
    public void configure(Context context) {
        synchronized (this) {
            if (context == null) {
                logger.error("Hessc nettyServer config is null, will close!");
                return;
            }
            try {
                this.host = context.getString(BasicConfigurationConstants.CONFIG_SERVER_HOST, IpUtils.getLocalIp());
                this.port = context.getInteger(BasicConfigurationConstants.CONFIG_SERVER_PORT, 18866);
                ApplicationServerContext.init(host, port);
                String registerAddress = context.getString(BasicConfigurationConstants.CONFIG_REGISTER_ADDRESS);
                String registerScheme = context.getString(BasicConfigurationConstants.CONFIG_REGISTER_SCHEME);
                String registerAuth = context.getString(BasicConfigurationConstants.CONFIG_REGISTER_AUTH);
                this.serviceRegistry = new ServiceRegistry(registerAddress, registerScheme, registerAuth);
                redisSource.configure(context);

            } catch (Exception e) {
                logger.error("configure Hessc server error: {}!", e.getMessage());
                System.exit(-1);
            }
        }
    }


    public class NettyServerRunner extends Thread {
        @Override
        public void run() {
            // EventLoopGroup 管理的线程数可以通过构造函数设置，如果没有设置，默
            // 认取 -Dio.netty.eventLoopThreads，如果该系统参数也没有指定，则为可用的 CPU 内核数 × 2
            // bossGroup 线程组实际就是 Acceptor 线程池，负责处理客户端的 TCP 连接请求，
            // 如果系统只有一个服务端端口需要监听，则建议 bossGroup 线程组线程数设置为 1
            // workerGroup 是真正负责 I/O 读写操作的线程组，通过 ServerBootstrap 的 group 方法进行设置，用于后续的 Channel 绑定。
            EventLoopGroup bossGroup = new NioEventLoopGroup();
            EventLoopGroup workerGroup = new NioEventLoopGroup();
            try {
                ServerBootstrap bootstrap = new ServerBootstrap();
                bootstrap.group(bossGroup, workerGroup).channel(NioServerSocketChannel.class)
                        // 服务端还是客户端都进行了handler的设置，通过添加hanlder，
                        // 我们可以监听Channel的各种动作以及状态的改变，包括连接，绑定，接收消息等。
                        // handler在初始化时就会执行，而childHandler会在客户端成功connect后才执行，这是两者的区别。
                        .childHandler(new RpcServerInitializer(serviceMap))
                        .option(ChannelOption.SO_BACKLOG, 128)
                        .childOption(ChannelOption.SO_KEEPALIVE, true);

                ChannelFuture future = bootstrap.bind(host, port).sync();
                // 注册zk rpc原数据信息
                // Path: /registry/data/hashcode
                if (serviceRegistry != null) {
                    serviceRegistry.registerService(host, port, serviceMap);
                }
                logger.info("Server started on port {}", port);
                Channel channel = future.channel();
                serverChannel = channel;
                channel.closeFuture().sync();
            } catch (Exception e) {
                if (e instanceof InterruptedException) {
                    logger.info("Rpc server remoting server stop");
                } else {
                    logger.error("Rpc server remoting server error", e);
                }
            } finally {
                try {
                    if (serviceRegistry != null) {
                        serviceRegistry.unregisterService();
                    }
                    workerGroup.shutdownGracefully();
                    bossGroup.shutdownGracefully();
                } catch (Exception ex) {
                    logger.error(ex.getMessage(), ex);
                }
            }
        }
    }
}
