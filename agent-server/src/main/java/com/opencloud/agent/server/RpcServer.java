package com.opencloud.agent.server;

import com.opencloud.agent.annotation.NettyRpcService;
import com.opencloud.agent.lifecycle.LifecycleInit;
import org.reflections.Reflections;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Set;

public class RpcServer extends NettyServer implements LifecycleInit {

    private static final Object PKG_BASE = RpcServer.class.getPackage().getName();
    private static final Logger logger = LoggerFactory.getLogger(RpcServer.class);

    public RpcServer() {
        this.init();
    }

    @Override
    public void init() {
        Set<Class<?>> classSet = new Reflections(PKG_BASE).getTypesAnnotatedWith(NettyRpcService.class);
        for (Class serviceBean : classSet) {
            NettyRpcService nettyRpcService = (NettyRpcService) serviceBean.getAnnotation(NettyRpcService.class);
            try {
                String interfaceName = nettyRpcService.value().getName();
                String version = nettyRpcService.version();
                super.addService(interfaceName, version, serviceBean.newInstance());
            } catch (Exception e) {
                logger.error("Hessc service protocol is null, context error ", e);
                System.exit(-1);
            }
        }
    }

}
