package com.opencloud.agent.star;

import com.opencloud.agent.config.AgentComponent;

import static com.opencloud.config.RegisterType.CLIENT;


public class StarServerBootstrap {

    public static void main(String[] args) {
        // 启动
        AgentComponent.run(CLIENT, true);
    }
}
