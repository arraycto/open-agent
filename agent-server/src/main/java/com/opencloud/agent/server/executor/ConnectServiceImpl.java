package com.opencloud.agent.server.executor;

import com.opencloud.agent.annotation.NettyRpcService;
import com.opencloud.agent.server.context.ApplicationServerContext;
import com.opencloud.agent.service.ConnectService;
import lombok.extern.slf4j.Slf4j;

import static com.opencloud.agent.BasicConfigurationConstants.VERSION_1;

@Slf4j
@NettyRpcService(value = ConnectService.class, version = VERSION_1)
public class ConnectServiceImpl implements ConnectService {

    public ConnectServiceImpl() {

    }

    @Override
    public String connect(int messageType) {
        return ApplicationServerContext.setSocketInfoMessageType(messageType);
    }
}
