package com.opencloud.agent.executor;

import com.opencloud.agent.ExecutorStatus;
import com.opencloud.agent.util.JsonUtil;
import lombok.Data;

@Data
public class ExecutorFromBrower {

    private String command;

    private ExecutorStatus status;

    private String clientId;

    private String sessionId;

    private long executeId;

    private String browerIp;

    private Boolean isExecutor;

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
