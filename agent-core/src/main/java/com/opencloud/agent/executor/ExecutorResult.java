package com.opencloud.agent.executor;


import com.opencloud.agent.util.JsonUtil;
import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExecutorResult extends BaseExecutor {

    private String proxyIp;

    private String result;

    private String command;

    public ExecutorResult() {
    }

    @Override
    public String toString() {
        return JsonUtil.objectToJson(this);
    }
}
