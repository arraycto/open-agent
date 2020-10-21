package com.opencloud.agent;

import com.opencloud.agent.config.AgentComponent;

import static com.opencloud.config.RegisterType.SERVER;


public class ChannelServerBootstrap {

    public static void main(String[] args) {
        // 启动
        AgentComponent.run(SERVER, true);
    }
}
