package com.opencloud.agent.config;

import com.google.common.collect.Lists;
import com.google.common.eventbus.EventBus;
import com.google.common.eventbus.Subscribe;
import com.opencloud.agent.AgentServer;
import com.opencloud.agent.lifecycle.LifecycleAware;
import com.opencloud.agent.lifecycle.LifecycleState;
import com.opencloud.agent.lifecycle.LifecycleSupervisor;
import com.opencloud.config.RegisterType;
import com.opencloud.config.exception.ParserException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.File;
import java.util.ArrayList;
import java.util.List;
import java.util.Map;
import java.util.concurrent.locks.ReentrantLock;

public class AgentComponent {
    private static final Logger logger = LoggerFactory.getLogger(AgentComponent.class);
    private static MaterializedConfiguration materializedConfiguration;
    private final List<LifecycleAware> components;
    private final LifecycleSupervisor supervisor;
    private final ReentrantLock lifecycleLock = new ReentrantLock();

    public AgentComponent(List<LifecycleAware> components) {
        this.components = components;
        supervisor = new LifecycleSupervisor();
    }

    public AgentComponent() {
        this(new ArrayList<>(0));
    }

    public static void run(RegisterType server, boolean reload) {
        EngineContext.applicationInitialization(server);
        String engineStorePath = server.getStoreName();
        try {
            // 以下操作是核心操作，解析application.properties内容
            // 测试用3秒，原始值30秒
            int reloadInterval = 3;
            File configurationFile = new File(engineStorePath);
            if (!configurationFile.exists()) {
                throw new ParserException("The specified configuration file does not exist: " + engineStorePath);
            }
            AgentComponent agent;
            List<LifecycleAware> components = Lists.newArrayList();
            // 热加载
            EventBus eventBus = new EventBus("agent-channel-event-bus");
            if (reload) {
                PollingPropertiesFileConfigurationProvider configurationProvider = new PollingPropertiesFileConfigurationProvider(configurationFile, eventBus, reloadInterval);
                components.add(configurationProvider);
                agent = new AgentComponent(components);
                eventBus.register(agent);
                agent.start();
            } else {
                PropertiesFileConfigurationProvider configurationProvider = new PropertiesFileConfigurationProvider(configurationFile);
                agent = new AgentComponent();
                eventBus.register(agent);
                eventBus.post(configurationProvider.getConfiguration());
            }


            final AgentComponent appReference = agent;
            Runtime.getRuntime().addShutdownHook(new Thread("agent-shutdown-hook") {
                @Override
                public void run() {
                    appReference.stop();
                }
            });
        } catch (ParserException e) {
            logger.error("A fatal error occurred while running. Exception follows.", e);
            System.exit(-1);
        }

    }

    public void start() {
        lifecycleLock.lock();
        try {
            // 启动监听管理服务
            supervisor.start();
            for (LifecycleAware component : components) {
                supervisor.supervise(component, LifecycleState.START);
            }
        } finally {
            lifecycleLock.unlock();
        }
    }

    public void stop() {
        lifecycleLock.lock();
        try {
            supervisor.stop();
            for (LifecycleAware component : components) {
                supervisor.unsupervise(component);
            }
        } finally {
            lifecycleLock.unlock();
        }
    }

    /**
     * 监听回调application.properties配置文件
     */
    @Subscribe
    public void handleConfigurationEvent(MaterializedConfiguration conf) {
        try {
            lifecycleLock.lockInterruptibly();
            stopAllComponents();
            startAllComponents(conf);
        } catch (InterruptedException e) {
            logger.info("Interrupted while trying to handle configuration event");
        } finally {
            if (lifecycleLock.isHeldByCurrentThread()) {
                lifecycleLock.unlock();
            }
        }
    }

    private void startAllComponents(MaterializedConfiguration newNaterializedConfiguration) {
        logger.info("============================ Starting new configuration:{} ============================", newNaterializedConfiguration);
        materializedConfiguration = newNaterializedConfiguration;
        for (Map.Entry<String, AgentServer> entry : newNaterializedConfiguration.getAgentServers().entrySet()) {
            try {
                logger.info("[**********Starting {} Server **********]", entry.getKey());
                supervisor.supervise(entry.getValue(), LifecycleState.START);
            } catch (Exception e) {
                logger.error("[**********Error while starting {},{},**********]", entry.getValue(), e);
            }
        }
    }

    private void stopAllComponents() {
        if (materializedConfiguration != null) {
            logger.info("============================Shutting down configuration: {} ============================", materializedConfiguration);
            for (Map.Entry<String, AgentServer> entry : materializedConfiguration.getAgentServers().entrySet()) {
                try {
                    logger.info("[**********Stopping Server {} **********]" + entry.getKey());
                    supervisor.unsupervise(entry.getValue());
                } catch (Exception e) {
                    logger.error("[**********Error while stopping {},{} **********]", entry.getValue(), e);
                }
            }
        }
    }

}
