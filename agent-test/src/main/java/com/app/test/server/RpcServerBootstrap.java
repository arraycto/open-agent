package com.app.test.server;

import com.app.test.service.*;
import com.opencloud.agent.server.RpcServer;
import com.opencloud.agent.server.executor.ConnectServiceImpl;
import com.opencloud.agent.service.ConnectService;
import com.opencloud.config.Context;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.opencloud.agent.BasicConfigurationConstants.CONFIG_REGISTER_ADDRESS;
import static com.opencloud.agent.BasicConfigurationConstants.CONFIG_SERVER_PORT;

public class RpcServerBootstrap {
    private static final Logger logger = LoggerFactory.getLogger(RpcServerBootstrap.class);

    public static void main(String[] args) throws InterruptedException {
        RpcServer rpcServer = new RpcServer();
        Context context = new Context();
        context.put(CONFIG_SERVER_PORT, "18866");
        context.put(CONFIG_REGISTER_ADDRESS, "10.10.40.40:2181");
        rpcServer.configure(context);

        HelloService helloService1 = new HelloServiceImpl();
        rpcServer.addService(HelloService.class.getName(), "1.0", helloService1);
        HelloService helloService2 = new HelloServiceImpl2();
        rpcServer.addService(HelloService.class.getName(), "2.0", helloService2);
        PersonService personService = new PersonServiceImpl();
        rpcServer.addService(PersonService.class.getName(), "", personService);
        ConnectService connectService = new ConnectServiceImpl();
        rpcServer.addService(ConnectService.class.getName(), "1.0", connectService);
        try {
            rpcServer.start();
        } catch (Exception ex) {
            logger.error("Exception: {}", ex.getMessage());
        }
//        Thread.sleep(4000);
//        rpcServer.stop();
//        Thread.sleep(4000);
//        rpcServer.start();
    }
}
