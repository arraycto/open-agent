package com.opencloud.agent.executor;


import com.opencloud.agent.util.JsonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExecutorFromProxy extends BaseExecutor {

    private String proxyIp;

    private String command;

    private String error;

    public ExecutorFromProxy() {
    }

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
