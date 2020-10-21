package com.opencloud.agent.executor;

import lombok.Data;
import lombok.EqualsAndHashCode;

@EqualsAndHashCode(callSuper = true)
@Data
public class ExecutorFromServer extends BaseExecutor {

    private String proxyIp;

    private String command;

    private String args;
}
