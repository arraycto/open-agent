package com.opencloud.agent.executor;

import com.opencloud.agent.ExecutorStatus;
import lombok.Data;

import java.util.Date;

@Data
public class BaseExecutor {

    protected int type;
    protected Long executeId;
    protected ExecutorStatus executeStatus;
    protected String agentChannelId;
    protected String proxyChannelId;
    private String clientId;
    private boolean isExecutor;
    private String browerIp;
    private Long executeTime;
}
